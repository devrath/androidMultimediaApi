package com.example.code.modules

import android.annotation.SuppressLint
import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaFormat
import android.media.MediaMuxer
import com.google.android.exoplayer2.C
import timber.log.Timber
import java.lang.Exception
import java.nio.ByteBuffer

class DownloadPartialVideo : PartialVideoDownloadImpl{

    companion object {
        const val AUDIO_PREFIX = "audio/"
        const val VIDEO_PREFIX = "video/"
        const val UND = "und"
    }

    private val TAG: String = DownloadPartialVideo::class.java.simpleName
    private var extractor: MediaExtractor? = null
    private var muxer: MediaMuxer? = null

    private var inputBufferSize = -1

    enum class State {
        SUCCESS, FAILURE
    }

    operator fun invoke(
        url: String, destinationPath: String,
        startTimeInMs: Long, endTimeInMs: Long
    ) {
        // Print Track-Information
        Timber.tag(TAG).d(
            "Downloading from source:: $url"
                .plus("Destination path:: $destinationPath")
                .plus("\n").plus("StartTime:: $startTimeInMs")
                .plus("\n").plus("EndTime:: $endTimeInMs")
        )
        try {
            extractor?.apply { MediaExtractor().setDataSource(url) }
            muxer?.apply { MediaMuxer(destinationPath,MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4) }
        }catch (ex: Exception){
            Timber.tag(TAG).d("Error::-> ${ex.message}")
        }
    }

    fun release() {
        extractor?.release()
    }

    private fun addTracksToMuxerAndGetIndexMap(
        trackCount: Int,
        muxer: MediaMuxer
    ): HashMap<Int, Int> {
        val extractorToMuxerTrackIndexMap: HashMap<Int, Int> = HashMap(trackCount)
        Timber.tag(TAG).d("Number of tracks::-> $trackCount")

        if(trackCount>0){
            for(i in 0 until trackCount){
                val format = extractor?.getTrackFormat(i)
                val mime = format?.getString(MediaFormat.KEY_MIME)

                if (mime?.startsWith(AUDIO_PREFIX) == true ||
                    mime?.startsWith(VIDEO_PREFIX) == true) {

                    extractor?.selectTrack(i)
                    extractorToMuxerTrackIndexMap[i] = muxer.addTrack(format)

                    Timber.d(TAG, "i : $i, Index : ${extractorToMuxerTrackIndexMap[i]}, Mime : $mime")

                    if (format.containsKey(MediaFormat.KEY_MAX_INPUT_SIZE)) {
                        val newSize = format.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE)
                        inputBufferSize = if (newSize > inputBufferSize) newSize else inputBufferSize
                    }
                }

                if (inputBufferSize < 0) {
                    inputBufferSize = DEFAULT_BUFFER_SIZE
                }

            }
        }else{
            Timber.tag(TAG).d("There are no tracks available")
        }

        return extractorToMuxerTrackIndexMap
    }

    override suspend fun downloadVideo(url: String, destinationPath: String,
                                       startTimeInMs: Long, endTimeInMs: Long
    ) {

        muxer?.let { muxer ->
            extractor?.let { extractor ->
                val extractorToMuxerTrackIndexMap: HashMap<Int, Int> = addTracksToMuxerAndGetIndexMap(extractor.trackCount, muxer)

                Timber.d(TAG, "extractorToMuxerIndexMap : $extractorToMuxerTrackIndexMap")

                if (startTimeInMs > 0) {
                    extractor.seekTo(startTimeInMs * 1000, MediaExtractor.SEEK_TO_CLOSEST_SYNC)
                }

                //copySamplesToMuxer(startTimeInMs, extractorToMuxerTrackIndexMap, endTimeInMs, extractor, muxer, progress, result)
                copySamplesToMuxer(startTimeInMs, extractorToMuxerTrackIndexMap, endTimeInMs, extractor, muxer)
            }

        }

    }

    @SuppressLint("WrongConstant")
    private suspend fun copySamplesToMuxer(
        startTimeInMs: Long,
        extractorToMuxerTrackIndexMap: HashMap<Int, Int>,
        endTimeInMs: Long,
        extractor: MediaExtractor,
        muxer: MediaMuxer
    ) {
        Timber.d(TAG, "copySamplesToMuxer")

        val destinationBuffer = ByteBuffer.allocate(inputBufferSize)
        val bufferInfo = MediaCodec.BufferInfo()

        try {
            muxer.start()

            var shouldProcess = true

            while (shouldProcess) {

                bufferInfo.offset = 0
                bufferInfo.size = extractor.readSampleData(destinationBuffer, 0)
                bufferInfo.presentationTimeUs = extractor.sampleTime

                Timber.d(TAG, "BufferSize : ${bufferInfo.size}, PresentationTime : ${bufferInfo.presentationTimeUs}")

                extractorToMuxerTrackIndexMap[extractor.sampleTrackIndex]?.let { trackIndexInMuxer ->
                    shouldProcess = if (bufferInfo.size < 0) {
                        bufferInfo.size = 0
                        false
                    } else if (endTimeInMs > 0 && bufferInfo.presentationTimeUs > endTimeInMs * 1000) {
                        false
                    } else {
                        bufferInfo.flags = extractor.sampleFlags
                        muxer.writeSampleData(
                            trackIndexInMuxer, destinationBuffer,
                            bufferInfo
                        )
                        /*progress(
                            (((C.usToMs(bufferInfo.presentationTimeUs) - startTimeInMs) * 100f) / (endTimeInMs - startTimeInMs)).toInt()
                        )*/
                        extractor.advance()
                    }
                }

            }
            muxer.stop()

            //result(State.SUCCESS, "")
            Timber.d(TAG, "Success")
        } catch (e: Exception) {
            Timber.d(TAG, "Error : ${e.message}")

            //result(State.FAILURE, "Error : ${e.message}")
        } finally {
            releaseExtractorAndMuxer()
        }
    }


    private fun releaseExtractorAndMuxer() {
        Timber.d(TAG, "releaseMuxer")
        extractor?.release()
        extractor = null
        muxer?.release()
        muxer = null
    }

}

interface PartialVideoDownloadImpl {
    suspend fun downloadVideo(
        url: String, destinationPath: String,
        startTimeInMs: Long, endTimeInMs: Long
    )
}