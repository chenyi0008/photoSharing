package com.example.photosharing.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photosharing.R;
import com.example.photosharing.model.Comment;

import java.util.List;
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> comments;

    // 构造函数，用于传递评论列表数据
    public CommentAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 创建新的ViewHolder，加载 item_comment 布局
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        // 填充ViewHolder的数据
        Comment comment = comments.get(position);
        holder.userNameTextView.setText(comment.getUserName());
        holder.commentTextView.setText(comment.getContent());
    }

    @Override
    public int getItemCount() {
        // 返回评论列表的大小
        return comments.size();
    }

    // 定义CommentViewHolder类，用于表示每个评论项的视图
    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView userNameTextView, commentTextView;

        // 构造函数，绑定布局中的视图
        CommentViewHolder(View itemView) {
            super(itemView);
            userNameTextView = itemView.findViewById(R.id.userNameTextView);
            commentTextView = itemView.findViewById(R.id.commentTextView);
        }
    }
}
