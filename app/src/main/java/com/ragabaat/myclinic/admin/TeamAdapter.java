package com.ragabaat.myclinic.admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ragabaat.myclinic.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeamAdapter extends RecyclerView.Adapter<com.ragabaat.myclinic.admin.TeamAdapter.TeamViewHolder> {

    private List<TeamData> list;
    private Context context;

    public TeamAdapter(List<TeamData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.team_item_layout,parent,false);

        return  new TeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {

        TeamData data = list.get(position);
        holder.teamName .setText(data.getName());
        holder.teamBio .setText(data.getBio());
        try {
            Picasso.get().load(data.getImage()).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.teamUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, UpdateTeamActivity.class);
                intent.putExtra("name",data.getName());
                intent.putExtra("bio",data.getBio());
                intent.putExtra("key",data.getKey());
                intent.putExtra("image",data.getImage());

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class  TeamViewHolder extends RecyclerView.ViewHolder {


        private TextView teamName,teamBio;
        private CircleImageView imageView;
        private Button teamUpdateBtn;
       
        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);
            teamName = itemView.findViewById(R.id.teamNamedec);
            teamBio = itemView.findViewById(R.id.teamBio);
            imageView = itemView.findViewById(R.id.teamImagedec);
            teamUpdateBtn = itemView.findViewById(R.id.teamUpdate);

        }
    }
}
