# androidMultimediaApi
This repository demonstrates how to use android low level multimedia api's to create good experience


## `Low level API hierarchy`
<p align="center">
  <img src="https://github.com/devrath/androidMultimediaApi/blob/main/assets/hierarchy.png">
</p>

* When we want to do more than just `recording` a media and `capturing` a media. We need to use `Low level media API's`

## `Terminologies`

### `Muxing`
* `Muxing` is also called `multiplexing`.
* `Muxing` plays an important role in video processing.
* `Muxing` means weaving together many elements.
* We can also say as `taking multiple inputs` -> `Then` -> `Weaving them together` -> `Then` -> `Sending them over a single output line`.
* `Ex`: Multiplexing example we can consider multiple calls traveling on a wire and all are different, Thus a single wire is sending many signals simultaneously.
* It is an efficient way of sending multiple inputs in a single line. A handy way in broadcasting.
* `Muxing` is when you have more inputs than outputs. Here we are selecting some inputs in a collection of inputs and deciding which is the output.


### `Demuxing`
* `Demuxing` is also called `de-multiplexing`.
* `Demuxing` is an abbreviation of `de-multiplexing`.
* It is the process of reading a multi-part stream and saving each part.
* Parts of stream include `audio`, `video`, `subtitles`.
* `Demuxing` is when you have a large number of outputs than the inputs. You select which of the inputs from the outputs.

### `Muxing` and `Demuxing` in together.
* `Muxing` and `Demuxing` are just the reverse operations of each other.
* If you have something `muxed`, You can `demux` and vis-versa, If you have something `demux` you can `mux` it
* `Muxing it wrapping` and `Demusing is unwapping`

### `Media Containers`
* Some Examples for media containers are 
  * Mp4
  * WebM
  * Avi etc.
* These are some examples of the containers for media.
* We have `video`, `audio`, `subtitles` etc and all these are wrapped together. 
* `Wrapped together` is also called `muxed together`. 
* So when you are creating a media file like `mp4`, below are muxed together to form an output file.
  * We have video from the camera.
  * We have audio from the microphone.
* When you play that file of `mp4`. Your player demuxes it, It pulls out the video to play in `video`, It pulls out `audio` to play in the speaker if any subtitles it draws them to be drawn in the bottom.

## `MediaCodec`

<p align="center">
  <img src="https://developer.android.com/images/media/mediacodec_buffers.png">
</p>

### `Introduction`
* `MediaCodec` is used to access the `low-level` media codecs.
* `Low level` media codec include things like `encoder` and `decoder`.
* `Codec` processes input data to provide and generate output data. 
* It processes the data `asynchronously`
* It uses a set of input and output buffers as seen in the diagram above.

### `How it works`
* Client requests for the `buffer packets` from the `codec`.
* Then client populates the `buffer packets` with data and sends the data back to the codec for processing.
* The `codec` processes the `buffer packets` one by one and transforms them.
* Finally the client again requests it or receives the transformed `buffer packets`. 
* After receiving the `buffer packets` from the codec, The client utilizes them and sends the emptied `buffer packets` back to `codec`.

### `More information`
* `Codec` is used to decode a media file and encode it to another one.

## `MediaExtractor`

<p align="center">
  <img src="https://github.com/devrath/androidMultimediaApi/blob/main/assets/media_extractor.png">
</p>
* MediaExtractor facilitates the extraction of encoded, media data from a data source.

## `Media Player`

### `High-level diagram of media player flow`
<p align="center">
  <img src="https://github.com/devrath/androidMultimediaApi/blob/main/assets/high_level_diagram.png">
</p>

* Higher lever is represented as having a data source which can be a remote data source.
* The player that encodes/decodes the data source depending on the data source.
* There is a surface that plays the information from the player.

### `Detailed-level diagram of media player flow`
<p align="center">
  <img src="https://github.com/devrath/androidMultimediaApi/blob/main/assets/detaileddiagram.png">
</p>

* Adding to the points of higher-level diagram, Here we divide the `media player` into further parts
  * `MediaExtractor`
  * `Media Codec`
    * `Audio Decoder`
    * `Video Decoder`
  * Some items to handle the cryptic parts of the stream
    * `Media DRM`
    * `Media Crypto`
* `MediaExtractor` combined with the 2 `media-codecs` are used to extract the individual data packets from `data-source`.
* To handle the encrypted video, You will need a `media-DRM` to manage the session and `media-crypto` to interact with the video codec.


## `Media Recorder`

### `High-level diagram of media recorder flow`
<p align="center">
  <img src="https://github.com/devrath/androidMultimediaApi/blob/main/assets/media_recorder_high_level.png">
</p>

* Compared to `media player`, The `media recorder` is the inverse version of it. 

### `Detailed-level diagram of media recorder flow`
<p align="center">
  <img src="https://github.com/devrath/androidMultimediaApi/blob/main/assets/media_recorder_detailed_level.png">
</p>

* The media Recorder is a combination of media codecs that include `audio/video` codecs.
* The data from the codecs is passed into the `media muxer` that generates a `video file`.


