package kinopoisk.cinema.presentation.customview

import androidx.recyclerview.widget.RecyclerView


class CustomLayoutManager : RecyclerView.LayoutManager() {

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
        RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )

    override fun onLayoutChildren(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ) {
        super.onLayoutChildren(recycler, state)
    }

    override fun onMeasure(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        widthSpec: Int, heightSpec: Int
    ) {
        super.onMeasure(recycler, state, widthSpec, heightSpec)
    }

    override fun scrollVerticallyBy(
        dy: Int, recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        return super.scrollVerticallyBy(dy, recycler, state)
    }

}
