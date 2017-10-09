package rs.ac.uns.ftn.ori.keystrokelogger;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String NEXT = "NEXT";

    private static final String[] EXPECTED_INPUT = {"3", "6", "0", "1", NEXT};

    private List<Sample> samples = new ArrayList<>();

    private Sample currentSample = new Sample();

    @ViewsById({R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9})
    List<Button> buttons;

    @ViewById
    Button buttonNext;

    @ViewById
    TextView pin;

    @ViewById
    TextView sampleCountText;

    @ViewById
    EditText userIndex;

    private int sampleCount = 0;

    private int inputCount = 0;

    private long startPressTime = 0;
    private long endPressTime = 0;
    private long startTravelTime = 0;
    private long endTravelTime = 0;

    View.OnTouchListener numberListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    startPressTime = System.currentTimeMillis();

                    if (inputCount != 0) {
                        endTravelTime = System.currentTimeMillis();
                        currentSample.addTravelTime(endTravelTime - startTravelTime);
                    }

                    logInput(event);
                    final Button button = (Button) v;
                    pin.setText(pin.getText().toString() + button.getText());
                    handleInput(button.getText().toString());
                    currentSample.addPressure(event.getPressure());
                    currentSample.addArea(event.getSize());
                    break;
                case MotionEvent.ACTION_UP:
                    startTravelTime = System.currentTimeMillis();
                    endPressTime = System.currentTimeMillis();
                    currentSample.addHoldTime(endPressTime - startPressTime);
                    logInput(event);
                default:
            }
            return true;
        }
    };

    View.OnTouchListener nextListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    startPressTime = System.currentTimeMillis();
                    endTravelTime = System.currentTimeMillis();
                    currentSample.addTravelTime(endTravelTime - startTravelTime);
                    logInput(event);
                    pin.setText("");
                    handleInput(NEXT);
                    currentSample.addPressure(event.getPressure());
                    currentSample.addArea(event.getSize());
                    break;
                case MotionEvent.ACTION_UP:
                    endPressTime = System.currentTimeMillis();
                    currentSample.addHoldTime(endPressTime - startPressTime);
                    samples.add(currentSample);
                    currentSample = new Sample();
                    if (sampleCount == 20) {
                        saveSamples();
                        clear();
                    }
                    break;
                default:
            }
            return true;
        }
    };

    @AfterViews
    void initListeners() {
        for (Button button : buttons) {
            button.setOnTouchListener(numberListener);
        }
        buttonNext.setOnTouchListener(nextListener);
    }

    private void handleInput(String input) {
        if (EXPECTED_INPUT[inputCount].equals(input)) {
            inputCount++;
            inputCount %= 5;
            if (inputCount == 0) {
                sampleCount++;
                sampleCountText.setText(String.valueOf(sampleCount));
            }
        } else {
            InputErrorDialog.show(this);
        }
    }

    public void handleWrongInput() {
        inputCount = 0;
        pin.setText("");
        currentSample = new Sample();
    }

    private void clear() {
        inputCount = 0;
        sampleCount = 0;
        sampleCountText.setText("0");
        pin.setText("");
        currentSample = new Sample();
        samples = new ArrayList<>();
    }

    private void saveSamples() {
        try {
            CSVWriterHelper.write(samples, userIndex.getText().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Sample saved!", Toast.LENGTH_SHORT).show();
    }

    private void logInput(MotionEvent event) {
        Log.d(TAG, "pressure: " + event.getPressure());
        Log.d(TAG, "surface: " + event.getSize());
    }
}
