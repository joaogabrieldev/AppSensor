package tads.eaj.ufrn.terceiraprova_01.Fragments

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import tads.eaj.ufrn.terceiraprova_01.R
import tads.eaj.ufrn.terceiraprova_01.databinding.FragmentProximityBinding


@Suppress("DEPRECATION")
class ProximityFragment : Fragment(), SensorEventListener {

    lateinit var binding: FragmentProximityBinding
    lateinit var sensorManager: SensorManager;
    var sensor: Sensor? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_proximity, container, false)

        setHasOptionsMenu(true);
        setSensorStuff();

        return binding.root;
    }

    fun setSensorStuff(){
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event?.sensor?.type == Sensor.TYPE_PROXIMITY){
            val distance = event.values[0];
            binding.textProximity.text = "Distancia = ${distance.toInt()}";
            val color = if(distance > 0) Color.WHITE else Color.rgb(24,255,255)
            binding.textProximity.setBackgroundColor(color)
        }
    }

    override fun onResume() {
        super.onResume()
        sensor?.also { proximity ->
            sensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_FASTEST)
        }

    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this);
    }


}