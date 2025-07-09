package com.graissy.recordkeeper.cycling

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.edit
import com.graissy.recordkeeper.databinding.ActivityEditCyclingRecordBinding

class EditCyclingRecordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditCyclingRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditCyclingRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val record = intent.getStringExtra("Record")
        title = "$record Record"

        displayRecords(record)
        binding.buttonSave.setOnClickListener{
            saveRecord(record)
            finish()
        }

        binding.buttonDelete.setOnClickListener {
            clearRecord(record)
            finish()
        }
    }

    private fun displayRecords(record: String?) {
        val cyclingPreferences = getSharedPreferences("cycling", Context.MODE_PRIVATE)

        binding.editTextRecord.setText(cyclingPreferences.getString("$record record", null))
        binding.editTextDate.setText(cyclingPreferences.getString("$record date", null))
    }

    private fun saveRecord(rec: String?) {
        val record = binding.editTextRecord.text.toString()
        val date = binding.editTextDate.text.toString()

        val cyclingPreferences = getSharedPreferences("cycling", Context.MODE_PRIVATE)


        cyclingPreferences.edit {// we have access to the editor already!
            putString("$rec record", record)
            putString("$rec date", date)
        }
    }

    private fun clearRecord(record: String?) {
        val cyclingPreferences = getSharedPreferences("cycling", Context.MODE_PRIVATE)

        cyclingPreferences.edit {
            remove("$record record")
            remove("$record date")
        }

    }

}