package io.github.ryanhoo.music.ui.playlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.github.ryanhoo.music.R;
import io.github.ryanhoo.music.data.model.PlayList;
import io.github.ryanhoo.music.ui.common.AbstractFooterAdapter;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/9/16
 * Time: 10:16 PM
 * Desc: PlayListAdapter
 *
 * 在父类ListAdapter中设置长按和点击事件，并且通过createView绑定Item，在子类中重写createView方法中绑定具体的itemView,
 * 在AbstractFooterAdapter设置FootView的位置，在子类createFooterView方法中具体实现
 * 设置回调接口AddPlayListCallback，在PlayListFragment实现具体的操作
 *
 */
public class PlayListAdapter extends AbstractFooterAdapter<PlayList, PlayListItemView> {

    private Context mContext;

    private View mFooterView;
    private TextView textViewSummary;

    private AddPlayListCallback mAddPlayListCallback;

    public PlayListAdapter(Context context, List<PlayList> data) {
        super(context, data);
        mContext = context;
        //对数据更新进行监听，数据变化后在底部更新显示List的数量
        registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                updateFooterView();
            }
        });
    }

    @Override
    protected PlayListItemView createView(Context context) {
        return new PlayListItemView(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerView.ViewHolder holder = super.onCreateViewHolder(parent, viewType);
        //Play
        if (holder.itemView instanceof PlayListItemView) {
            final PlayListItemView itemView = (PlayListItemView) holder.itemView;
            //点击右边按钮进行进一步操作
            itemView.buttonAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    if (mAddPlayListCallback != null) {
                        mAddPlayListCallback.onAction(itemView.buttonAction, position);
                    }
                }
            });
        }
        return holder;
    }

    // Footer View，设置add play list底部View

    @Override
    protected boolean isFooterEnabled() {
        return true;
    }

    @Override
    protected View createFooterView() {
        if (mFooterView == null) {
            mFooterView = View.inflate(mContext, R.layout.item_play_list_footer, null);
            View layoutAddPlayList = mFooterView.findViewById(R.id.layout_add_play_list);
            //点击添加新的List,通过回调在PlayListFragment中进行操作
            layoutAddPlayList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAddPlayListCallback != null) {
                        mAddPlayListCallback.onAddPlayList();
                    }
                }
            });
            textViewSummary = (TextView) mFooterView.findViewById(R.id.text_view_summary);
        }
        updateFooterView();
        return mFooterView;
    }

    public void updateFooterView() {
        if (textViewSummary == null) return;

        int itemCount = getItemCount() - 1; // real data count
        if (itemCount > 1) {
            textViewSummary.setVisibility(View.VISIBLE);
            textViewSummary.setText(mContext.getString(R.string.mp_play_list_footer_end_summary_formatter, itemCount));
        } else {
            textViewSummary.setVisibility(View.GONE);
        }
    }

    // Callback

    public void setAddPlayListCallback(AddPlayListCallback callback) {
        mAddPlayListCallback = callback;
    }

    /* package */ interface AddPlayListCallback {

        void onAction(View actionView, int position);

        void onAddPlayList();
    }
}
