package tads.eaj.ufrn.terceiraprova_01.Fragments

import android.content.Context
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
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import tads.eaj.ufrn.terceiraprova_01.R
import tads.eaj.ufrn.terceiraprova_01.databinding.FragmentGyroscopeBinding


class GyroscopeFragment : Fragment(), SensorEventListener {

    lateinit var binding: FragmentGyroscopeBinding
    private lateinit var sensorManager: SensorManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gyroscope, container, false)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setUpSensorStuff()
        return binding.root
    }

    private fun setUpSensorStuff() {
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)?.also { accelerometer ->
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
        if(event?.sensor?.type == Sensor.TYPE_GYROSCOPE){
            var valueZ = event.values[2]
            binding.textValue.text = "Valor: ${valueZ}"
            if(event.values[2] > 0.5f) { // anticlockwise
                binding.backGyro.setBackgroundColor(Color.rgb(7,39,89));
                binding.textSentido.text = "Sentido anti horário"
            } else if(event.values[2] < -0.5f) { // clockwise
                binding.backGyro.setBackgroundColor(Color.BLACK);
                binding.textSentido.text = "Sentido horário"
            }
        }
    }
}