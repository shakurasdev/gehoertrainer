package com.example.a26ss_gehoertrainer.ui.highscore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a26ss_gehoertrainer.databinding.ItemHighscoreBinding
import com.example.a26ss_gehoertrainer.model.HighscoreRow
import com.example.a26ss_gehoertrainer.model.SpielergebnisModel

class HighscoreAdapter(
    private val items: List<HighscoreRow>
) : RecyclerView.Adapter<HighscoreAdapter.ViewHolder>() {

    class ViewHolder(
        val binding: ItemHighscoreBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val binding =
            ItemHighscoreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val item = items[position]

        holder.binding.tvPlace.text =
            item.place.toString()

        holder.binding.tvPercent.text =
            "${item.percent}%"

        holder.binding.tvDate.text =
            item.date

        holder.binding.tvPlayerId.text =
            item.playerId

        holder.binding.tvInstallationId.text =
            item.installationId
    }

    override fun getItemCount() = items.size
}