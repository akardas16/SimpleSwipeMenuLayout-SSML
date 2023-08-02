package io.sulek.ssmlsample

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.github.hariprasanths.bounceview.BounceView
import io.sulek.ssml.OnSwipeListener
import kotlinx.android.synthetic.main.sample_holder.view.*
import java.util.*

class SampleAdapter(private val context: Context) : RecyclerView.Adapter<SampleAdapter.SampleHolder>() {

    private val inflater = LayoutInflater.from(context)
    private val items = mutableListOf<SampleData>()
    private val random = Random()

    init {
        prepareSampleData()
    }

    private fun prepareSampleData() {
        for (itemNumber in 1..30) {
            val randomNumberOfDescriptions = random.nextInt(2)
            var description = ""

            for (d in 1..randomNumberOfDescriptions) description += "description [$d]\n"

            items.add(
                SampleData(
                    "Item [$itemNumber] should have [$randomNumberOfDescriptions] lines of description",
                    description
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleHolder {
        return SampleHolder(inflater.inflate(R.layout.sample_holder, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SampleHolder, position: Int) {
        holder.setSampleData(context = context, sampleData = items[position])
    }

    inner class SampleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setSampleData(context: Context,sampleData: SampleData) {
            itemView.swipeContainer.setOnSwipeListener(object : OnSwipeListener {
                override fun onSwipe(isExpanded: Boolean) {
                    sampleData.isExpanded = isExpanded
                }
            })

            val myView = itemView.swipeContainer.findViewById<FrameLayout>(R.id.button2)
            BounceView.addAnimTo(myView).setScaleForPopOutAnim(1f,1f).setScaleForPushInAnim(0.96f,0.96f)
            myView.setOnClickListener {
                Toast.makeText(context,"Hello",Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed(Runnable {
                    itemView.swipeContainer.apply(false)
                },400)
            }

            itemView.titleText.text = sampleData.title
            itemView.descriptionText.text = sampleData.description
            itemView.swipeContainer.apply(sampleData.isExpanded)
        }
    }
}