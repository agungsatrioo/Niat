package id.sera5.niat.ui.misc;

import android.os.Bundle;

import com.rygelouv.audiosensei.player.AudioSenseiPlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.sera5.niat.R;
import id.sera5.niat.ui.BaseActivity;

public class AudioActivity extends BaseActivity {

    @BindView(R.id.audio_player)
    AudioSenseiPlayerView audioPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        ButterKnife.bind(this);

        setTitle("Audio");

        setBackButton();

        audioPlayer.setAudioTarget(R.raw.audio);
    }
}
