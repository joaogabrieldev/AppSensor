package tads.eaj.ufrn.terceiraprova_01.Fragments

import android.content.Context.SENSOR_SERVICE
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import tads.eaj.ufrn.terceiraprova_01.R
import tads.eaj.ufrn.terceiraprova_01.databinding.FragmentAccelerometerBinding


class AccelerometerFragment : Fragment(), SensorEventListener {

    lateinit var binding: FragmentAccelerometerBinding
    private lateinit var sensorManager: SensorManager
    var sensor: Sensor? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_accelerometer, container, false)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setUpSensorStuff()
        return binding.root
    }

    private fun setUpSensorStuff() {
        sensorManager = activity?.getSystemService(SENSOR_SERVICE) as SensorManager

        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { accelerometer ->
            sensorManager.registerListener(
                this,
                accelerometer,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {

            val sides = event.values[0]
            val upDown = event.values[1]

            binding.tvSquare.apply {
                rotationX = upDown * 3f
                rotationY = sides * 3f
                rotation = -sides
                translationX = sides * -10
                translationY = upDown * 10
            }

            val color = if (upDown.toInt() == 0 && sides.toInt() == 0) Color.rgb(24,255,255) else Color.WHITE
            binding.tvSquare.setBackgroundColor(color)

            binding.tvSquare.text = "Subir/Descer: ${upDown.toInt()}\nEsquerda/Direita ${sides.toInt()}"

        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this);
    }


}