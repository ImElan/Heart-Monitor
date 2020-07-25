package app.android.heartmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {

    private LineGraphSeries<DataPoint> patientGraph;
    private double x_axis,y_axis;
    private GraphView graphView;
    private ArrayList<String> mValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        mValues = getIntent().getStringArrayListExtra("values");
        graphView = findViewById(R.id.graph_id);
        patientGraph = new LineGraphSeries<>();

        x_axis = 0;
        for(int i=0;i<mValues.size();i++)
        {
            x_axis += 0.1;
            y_axis = Double.parseDouble(mValues.get(i));
            patientGraph.appendData(new DataPoint(x_axis,y_axis),true,100);
        }
        graphView.addSeries(patientGraph);

    }
}
