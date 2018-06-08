package com.tejasjoshi.noteappkotlin

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.facebook.stetho.Stetho
import kotlinx.android.synthetic.main.activity_note.*
import okhttp3.OkHttpClient
import android.view.ViewGroup
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.View.MeasureSpec
import android.view.View.MeasureSpec.UNSPECIFIED
import android.view.View.MeasureSpec.AT_MOST
import android.view.View.MeasureSpec.EXACTLY
import android.view.View.MeasureSpec.makeMeasureSpec
import android.view.View.MeasureSpec.getMode




class NoteActivity : AppCompatActivity() {

    var notes = ArrayList<NoteModel>();
    var noteAdapter :  NoteAdapter? =null ;
    private var db: NoteDatabseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        Stetho.initializeWithDefaults(this)



        db = NoteDatabseHelper(this)
        db!!.insertIntoData("THis is in to the database", "tejas")

        var notelist : ArrayList<NoteModel> =db!!.getAllData();

        println("Thread id before " + Thread.currentThread())

        val handler = object : Handler() {

            override fun handleMessage(msg: Message) {
                //TODO: Handle different types of messages
                if(msg.what==1001)
                {
                    recycler_view.layoutManager = MyLinearLayoutManager(this@NoteActivity,LinearLayoutManager.VERTICAL, false)
                    recycler_view.adapter = noteAdapter
                }
            }
        }

        var thread: Thread
        try {
            thread = Thread(Runnable {

                noteAdapter = NoteAdapter(notelist);
                handler.sendEmptyMessage(1001)

            })
            thread.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    //Database Handling functions

    fun createNote(note: String): Boolean {
        return true;
    }

    fun updateNote(id: Int): Boolean {
        return true;
    }

    fun deleteNote(id: Int): Boolean {

        return true;
    }
    fun getAllNotes(): ArrayList<NoteModel> {

        val listModels = ArrayList<NoteModel>()

        return listModels ;
    }

    inner class MyLinearLayoutManager(context: Context, orientation: Int, reverseLayout: Boolean) : LinearLayoutManager(context, orientation, reverseLayout) {

        private val mMeasuredDimension = IntArray(2)

        init {
            isAutoMeasureEnabled = false
        }

        override fun onMeasure(recycler: RecyclerView.Recycler?, state: RecyclerView.State?,
                               widthSpec: Int, heightSpec: Int) {
            val widthMode = View.MeasureSpec.getMode(widthSpec)
            val heightMode = View.MeasureSpec.getMode(heightSpec)
            val widthSize = View.MeasureSpec.getSize(widthSpec)
            val heightSize = View.MeasureSpec.getSize(heightSpec)
            var width = 0
            var height = 0
            for (i in 0 until itemCount) {


                if (orientation == LinearLayoutManager.HORIZONTAL) {

                    measureScrapChild(recycler, i,
                            View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                            heightSpec,
                            mMeasuredDimension)

                    width = width + mMeasuredDimension[0]
                    if (i == 0) {
                        height = mMeasuredDimension[1]
                    }
                } else {
                    measureScrapChild(recycler, i,
                            widthSpec,
                            View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                            mMeasuredDimension)
                    height = height + mMeasuredDimension[1]
                    if (i == 0) {
                        width = mMeasuredDimension[0]
                    }
                }
            }
            when (widthMode) {
                View.MeasureSpec.EXACTLY -> width = widthSize
            }

            when (heightMode) {
                View.MeasureSpec.EXACTLY -> height = heightSize
            }

            setMeasuredDimension(width, height)
        }

        private fun measureScrapChild(recycler: RecyclerView.Recycler?, position: Int, widthSpec: Int,
                                      heightSpec: Int, measuredDimension: IntArray) {
            val view = recycler!!.getViewForPosition(position)
            recycler.bindViewToPosition(view, position)
            if (view != null) {
                val p = view.layoutParams as RecyclerView.LayoutParams
                val childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec,
                        paddingLeft + paddingRight, p.width)
                val childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec,
                        paddingTop + paddingBottom, p.height)
                view.measure(childWidthSpec, childHeightSpec)
                measuredDimension[0] = view.measuredWidth + p.leftMargin + p.rightMargin
                measuredDimension[1] = view.measuredHeight + p.bottomMargin + p.topMargin
                recycler.recycleView(view)
            }
        }
    }
}
