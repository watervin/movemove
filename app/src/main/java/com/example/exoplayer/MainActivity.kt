package com.example.exoplayer


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import android.net.Uri
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView


class MainActivity : AppCompatActivity() {
    private var videoPlayer: SimpleExoPlayer? = null
    private var sampleUrl = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"
    private lateinit var video_player_view: PlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        video_player_view = findViewById(R.id.video_player_view);

        // ExoPlayer 인스턴스를 생성하고 소스를 플레이에 할당하여 비디오 플레이어 초기화
        videoPlayer = SimpleExoPlayer.Builder(this).build()
        video_player_view?.player = videoPlayer
        buildMediaSource()?.let {
            videoPlayer?.prepare(it)
        }

    }

    // MediaSource: 영상에 출력할 미디어 정보를 가져오는 클래스
    private fun buildMediaSource(): MediaSource? {
        val dataSourceFactory = DefaultDataSourceFactory(this, "sample")
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse(sampleUrl))
    }

    // 일시중지
    override fun onResume() {
        super.onResume()
        videoPlayer?.playWhenReady = true
    }

    // 정지
    override fun onStop() {
        super.onStop()
        videoPlayer?.playWhenReady = false
        if (isFinishing) {
            releasePlayer()
        }
    }

    // 종료
    private fun releasePlayer() {
        videoPlayer?.release()
    }
}