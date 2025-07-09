package com.graissy.recordkeeper.running

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.edit
import com.graissy.recordkeeper.databinding.ActivityEditRunningRecordBinding

class EditRunningRecordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditRunningRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditRunningRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val distance = intent.getStringExtra("Distance")
        title = "$distance Record"

        displayRecords(distance)
        binding.buttonSave.setOnClickListener{
            saveRecord(distance)
            finish()
        }

        binding.buttonDelete.setOnClickListener {
            clearRecord(distance)
            finish()
        }
    }

    private fun displayRecords(distance: String?) {
        val runningPreferences = getSharedPreferences("running", Context.MODE_PRIVATE)

        binding.editTextRecord.setText(runningPreferences.getString("$distance record", null))
        binding.editTextDate.setText(runningPreferences.getString("$distance date", null))
    }

    private fun saveRecord(distance: String?) {
        val record = binding.editTextRecord.text.toString()
        val date = binding.editTextDate.text.toString()

        val runningPreferences = getSharedPreferences("running", Context.MODE_PRIVATE)

        // *** OLD VERSION ***
//        val editor = runningPreferences.edit()
//        editor.putString("record", record)
//        editor.putString("date", date)
//        editor.apply()

        runningPreferences.edit {// we have access to the editor already!
            putString("$distance record", record)
            putString("$distance date", date)
        }
    }

    private fun clearRecord(record: String?) {
        val runningPreferences = getSharedPreferences("running", Context.MODE_PRIVATE)

        runningPreferences.edit {
            remove("$record record")
            remove("$record date")
        }

    }
}