package michal.swimlabmenus;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int padding;

    public VerticalSpaceItemDecoration(int padding) {
        this.padding = padding;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.bottom = padding;
        outRect.right = padding;
    }
}