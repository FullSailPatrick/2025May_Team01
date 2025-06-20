package com.example.pnp2_newproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class CreateFlashCardsScreen : AppCompatActivity()
{
    private lateinit var btnAdd: com.google.android.material.floatingactionbutton.FloatingActionButton
    lateinit var viewModel: FlashCardViewModel
    private lateinit var rvList : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.create_flashcards_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(findViewById(R.id.FlashCards_toolbar))
        supportActionBar?.title = "FlashCards"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val buttonView = findViewById<Button>(R.id.HomeFromFlashCards)
        btnAdd = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.btnAdd)
        rvList = findViewById(R.id.rvList)
        val flashCardRepository = FlashCardRepository(FlashCardDatabase(this))
        val factory = FlashCardViewModelFactory(flashCardRepository)

        // Initialised View Model
        viewModel = ViewModelProvider(this, factory)[FlashCardViewModel::class.java]
        val flashcardAdapter = FlashCardAdapter(listOf(), viewModel)
        rvList.layoutManager = LinearLayoutManager(this)
        rvList.adapter = flashcardAdapter

        // To display all items in recycler view
        viewModel.allFlashCardItems().observe(this)
        {
            flashcardAdapter.list = it
            flashcardAdapter.notifyItemInserted(flashcardAdapter.list.size)
            flashcardAdapter.notifyDataSetChanged()

            // on ClickListener on button to open dialog box
            btnAdd.setOnClickListener()
            {
                FlashCardItemDialog(this, object : DialogListener {
                    override fun onAddButtonClicked(item: FlashCardItems) {
                        viewModel.insert(item)
                    }
                }).show()
            }
        }

        buttonView.setOnClickListener()
        {
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
        }

        TimerManager.timerFinished.observe(this) {finished ->
            if(finished) {
                Toast.makeText(this,"Time For A Break", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->{
                finish()
                return true
            }

        }
        return super.onContextItemSelected(item)
    }
}

@Entity(tableName = "flashcard_items")
data class FlashCardItems(

    // create itemQuestion variable to store flashcard items.
    @ColumnInfo(name = "itemQuestion")
    var question: String,

    // create itemAnswer variable to store flashcard items.
    @ColumnInfo(name = "itemAnswer")
    var answer: String,

    ) {
    // Primary key is a unique key for different database.
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}

@Dao
interface FlashCardDao {

    // Insert function is used to insert data in database.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: FlashCardItems)

    // Delete function is used to delete data in database.
    @Delete
    suspend fun delete(item: FlashCardItems)

    // getAllFlashCardItems function is used to get all the data of database.
    @Query("SELECT * FROM flashcard_items")
    fun getAllFlashCardItems(): LiveData<List<FlashCardItems>>
}

@Database(entities = [FlashCardItems::class], version = 1)
abstract class FlashCardDatabase : RoomDatabase() {

    abstract fun getFlashCardDao(): FlashCardDao

    companion object {
        @Volatile
        private var instance: FlashCardDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                FlashCardDatabase::class.java, "FlashCardDatabase.db").build()
    }
}


class FlashCardRepository(private var db: FlashCardDatabase) {

    suspend fun insert(item: FlashCardItems) = db.getFlashCardDao().insert(item)
    suspend fun delete(item: FlashCardItems) = db.getFlashCardDao().delete(item)

    fun allFlashCardItems() = db.getFlashCardDao().getAllFlashCardItems()
}

class FlashCardViewModel(private var repository: FlashCardRepository) : ViewModel() {

    // In coroutines thread insert item in insert function.
    @OptIn(DelicateCoroutinesApi::class)
    fun insert(item: FlashCardItems) = GlobalScope.launch {
        repository.insert(item)
    }

    // In coroutines thread delete item in delete function.
    @OptIn(DelicateCoroutinesApi::class)
    fun delete(item: FlashCardItems) = GlobalScope.launch {
        repository.delete(item)
    }

    //Here we initialized allFlashCardItems function with repository
    fun allFlashCardItems() = repository.allFlashCardItems()

}

class FlashCardViewModelFactory(private var repository: FlashCardRepository):ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FlashCardViewModel(repository) as T
    }
}

