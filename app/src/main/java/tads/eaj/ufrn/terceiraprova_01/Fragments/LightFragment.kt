package tads.eaj.ufrn.terceiraprova_01.Fragments

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import tads.eaj.ufrn.terceiraprova_01.R
import tads.eaj.ufrn.terceiraprova_01.databinding.FragmentLightBinding


class LightFragment : Fragment(), SensorEventListener {

    lateinit var sensorManager: SensorManager
    var lights: Sensor? = null

    lateinit var binding: FragmentLightBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_light, container, false)

        setHasOptionsMenu(true);

        setSensorStuff()

        return binding.root
    }

    private fun setSensorStuff(){
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lights = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }


    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            val light = event.values[0]

            binding.sensorText.text = "Sensor: $light\n${brightness(light)}"
            binding.circularProgressBar.setProgressWithAnimation(light)
        }
    }

    private fun brightness(light: Float): String {
        return when (light.toInt()) {
            0 -> "Não há luz"
            in 1..60 -> "Pouco iluminado"
            in 61..5000 -> "Normal"
            in 5001..25000 -> "Muito iluminado"
            else -> "Você vai ficar cego de tanta luz"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onResume() {
        super.onResume()
        // Register a listener for the sensor.
        sensorManager.registerListener(this, lights, SensorManager.SENSOR_DELAY_FASTEST)
    }


    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

}