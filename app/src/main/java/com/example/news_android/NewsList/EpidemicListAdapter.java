package com.example.news_android.NewsList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news_android.EpidemicData;
import com.example.news_android.EpidemicRepo;
import com.example.news_android.NewsTextPage.NewsTextActivity;
import com.example.news_android.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class EpidemicListAdapter extends RecyclerView.Adapter<EpidemicListAdapter.EpidemicViewHolder>
{
    String[] districts;
    
    public EpidemicListAdapter(String[] districts)
    {
        this.districts = districts;
    }

    @NonNull
    @Override
    public EpidemicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.epidemic_chart, parent, false);
        return new EpidemicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EpidemicViewHolder holder, int position)
    {
        EpidemicRepo repo=new EpidemicRepo(holder.itemView.getContext());
        String[]location=districts[position].split("\\|");
        EpidemicData epidemicData=null;
        if(location.length==1)
        {
            epidemicData=repo.getEpidemicByCountry(location[0]);
        }
        else if(location.length==2)
        {
            epidemicData=repo.getEpidemicByProvince(location[1]);
        }


        ArrayList<PointValue>deadValues= new ArrayList<PointValue>();//points on the dead polyline
        ArrayList<PointValue>curedValues= new ArrayList<PointValue>();//points on the cured polyline
        ArrayList<PointValue>confirmedValues= new ArrayList<PointValue>();//points on the confirmed polyline

        if(epidemicData!=null)
        {
            String[]dateStr=epidemicData.beginDate.split("-");
            Calendar calendar=Calendar.getInstance();
            calendar.set(Integer.parseInt(dateStr[0]),Integer.parseInt(dateStr[1]),Integer.parseInt(dateStr[2]));
            ArrayList<AxisValue>axisValueList=new ArrayList<AxisValue>();
            for (int i = 0; i < epidemicData.dead.size(); i++)
            {
                PointValue dead=new PointValue(i,epidemicData.dead.get(i));
                PointValue cured=new PointValue(i,epidemicData.cured.get(i));
                PointValue confirmed=new PointValue(i,epidemicData.confirmed.get(i));
                if(calendar.get(Calendar.DAY_OF_MONTH)==1)
                {
                    AxisValue axisValue=new AxisValue(i);
                    axisValue.setLabel(calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DAY_OF_MONTH));
                    axisValueList.add(axisValue);
                }
                deadValues.add(dead);
                curedValues.add(cured);
                confirmedValues.add(confirmed);
                calendar.add(Calendar.DATE,1);
            }

            Line deadLine = new Line(deadValues).setColor(Color.RED);
            Line curedLine = new Line(curedValues).setColor(Color.BLUE);
            Line confirmedLine = new Line(confirmedValues).setColor(Color.YELLOW);

            deadLine.setCubic(false);
            deadLine.setHasPoints(false);
            deadLine.setStrokeWidth(3);
            curedLine.setCubic(false);
            curedLine.setHasPoints(false);
            curedLine.setStrokeWidth(3);
            confirmedLine.setCubic(false);
            confirmedLine.setHasPoints(false);
            confirmedLine.setStrokeWidth(3);
            ArrayList<Line> lines = new ArrayList<Line>();
            lines.add(deadLine);
            lines.add(curedLine);
            lines.add(confirmedLine);

            LineChartData data = new LineChartData();
            Axis axisX = new Axis();//x axis
            axisX.setValues(axisValueList);
            axisX.setHasLines(false).setTextColor(Color.BLACK).setTextSize(12).setName(districts[position]);;
            Axis axisY = new Axis();//y axis
            axisY.setTextColor(Color.BLACK).setTextSize(10);
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
            data.setLines(lines);
            holder.lcvEpidemic.setLineChartData(data);//Set data to the chart
        }
    }

    @Override
    public int getItemCount()
    {
        return districts.length;
    }

    static class EpidemicViewHolder extends RecyclerView.ViewHolder
    {
        LineChartView lcvEpidemic;// chart of the data
        public EpidemicViewHolder(@NonNull View itemView)
        {
            super(itemView);
            lcvEpidemic=itemView.findViewById(R.id.lcv_epidemic);
        }
    }
}
