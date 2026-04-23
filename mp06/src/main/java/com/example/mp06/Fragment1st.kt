package com.example.mp06

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mp06.databinding.Fragment1stBinding
import com.example.mp06.databinding.ItemMainBinding

class MyViewHolder(val binding: ItemMainBinding): RecyclerView.ViewHolder(binding.root)

class MyAdapter(val datas: MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        binding.itemData.text = datas[position]
    }
}

class MyDecoration(val context: Context): RecyclerView.ItemDecoration(){
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val width = parent.width
        val height = parent.height

        val dr = ResourcesCompat.getDrawable(context.resources, android.R.drawable.ic_menu_gallery, null)
        val drWidth = dr?.intrinsicWidth
        val drHeight = dr?.intrinsicHeight

        val left = width/2 - drWidth?.div(2) as Int
        val top = height/2 - drHeight?.div(2) as Int

        c.drawBitmap(
            BitmapFactory.decodeResource(context.resources, android.R.drawable.ic_menu_gallery),
            left.toFloat(),
            top.toFloat(),
            null
        )
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val index = parent.getChildAdapterPosition(view)+1

        if(index % 3 == 0)
            outRect.set(10, 10, 10, 60)
        else
            outRect.set(10, 10, 10, 0)

        view.setBackgroundColor(Color.parseColor("#28A0FF"))
        ViewCompat.setElevation(view, 20.0f)
    }
}

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment1st.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment1st : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        val binding =  Fragment1stBinding.inflate(inflater, container, false)

        val datas = mutableListOf<String>()
        for(i in 1..9){
            datas.add("Item $i")
        }

        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager
        val adapter = MyAdapter(datas)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(MyDecoration(activity as Context))

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment Fragment1st.
         */
        @JvmStatic
        fun newInstance() = Fragment1st()
    }
}