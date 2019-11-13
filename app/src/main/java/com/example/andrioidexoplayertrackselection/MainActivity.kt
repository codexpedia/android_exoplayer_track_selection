package com.example.andrioidexoplayertrackselection

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.andrioidexoplayertrackselection.exoplayer2.PlayerActivity
import com.example.andrioidexoplayertrackselection.exoplayer2.PlayerActivity.EXTENSION_EXTRA
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // Sample videos, more can be found in the media.exolist.json in the assets folder
    private val hls = "https://devstreaming-cdn.apple.com/videos/streaming/examples/bipbop_16x9/bipbop_16x9_variant.m3u8"
    private val dash = "http://www.youtube.com/api/manifest/dash/id/bf5bb2419360daf1/source/youtube?as=fmp4_audio_clear,fmp4_sd_hd_clear&sparams=ip,ipbits,expire,source,id,as&ip=0.0.0.0&ipbits=0&expire=19000000000&signature=51AF5F39AB0CEC3E5497CD9C900EBFEAECCCB5C7.8506521BFC350652163895D4C26DEE124209AA9E&key=ik0"
    private val mp4 = "https://html5demos.com/assets/dizzy.mp4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1.setOnClickListener { et_url.setText(hls) }
        btn2.setOnClickListener { et_url.setText(dash) }
        btn3.setOnClickListener { et_url.setText(mp4) }

        btn_play.setOnClickListener {
            val preferExtensionDecoders = false
            val abrAlgorithm = PlayerActivity.ABR_ALGORITHM_DEFAULT
            val intent = Intent(this, PlayerActivity::class.java)

            val videoUrl = et_url.text.toString()
            intent.putExtra(PlayerActivity.PREFER_EXTENSION_DECODERS_EXTRA, preferExtensionDecoders)
            intent.putExtra(PlayerActivity.ABR_ALGORITHM_EXTRA, abrAlgorithm)
            intent.data = Uri.parse(videoUrl)
            intent.action = PlayerActivity.ACTION_VIEW

            // this video url doesn't end with a file extension, therefore it needs a extension override
            // for buildMediaSource(Uri uri, @Nullable String overrideExtension) in the PlayerActivity
            if (videoUrl == dash) {
                intent.putExtra(EXTENSION_EXTRA, ".mpd")
            }

            startActivity(intent)
        }
    }

}
