package be.kdg.cityofideas.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import be.kdg.cityofideas.R
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.google.android.youtube.player.internal.d
import android.widget.Toast
import com.google.android.youtube.player.YouTubeInitializationResult
import android.util.Log
import be.kdg.cityofideas.adapters.YOUTUBE_API


class YoutubeFragment : YouTubePlayerSupportFragment() {

    override fun initialize(p0: String?, p1: YouTubePlayer.OnInitializedListener?) {
        super.initialize(p0, p1)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_youtube, container, false)
    }



}
