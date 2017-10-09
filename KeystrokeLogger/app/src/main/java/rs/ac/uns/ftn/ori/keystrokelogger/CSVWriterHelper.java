package rs.ac.uns.ftn.ori.keystrokelogger;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriterHelper {

    public static void write(List<Sample> samples, String userIndex) throws IOException {
        String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileName = "data.csv";
        String filePath = baseDir + File.separator + fileName;
        File file = new File(filePath);

        final boolean append = file.exists();
        final FileWriter fileWriter = new FileWriter(filePath, append);
        final CSVWriter csvWriter = new CSVWriter(fileWriter);

        String[] headers = {
                "person",
                "dwell_time_1",
                "dwell_time_2",
                "dwell_time_3",
                "dwell_time_4",
                "dwell_time_5",
                "flight_time_1",
                "flight_time_2",
                "flight_time_3",
                "flight_time_4",
                "pressure_1",
                "pressure_2",
                "pressure_3",
                "pressure_4",
                "pressure_5",
                "area_1",
                "area_2",
                "area_3",
                "area_4",
                "area_5"};

        if (!append) {
            csvWriter.writeNext(headers);
        }

        String[] data = new String[20];
        int k = 0;
        for (Sample sample : samples) {
            data[k++] = userIndex;

            for (int i = 0; i < 5; i++) {
                data[k++] = String.valueOf(sample.getHoldTimes()[i]);
            }

            for (int i = 0; i < 4; i++) {
                data[k++] = String.valueOf(sample.getTravelTimes()[i]);
            }

            for (int i = 0; i < 5; i++) {
                data[k++] = String.valueOf(sample.getPressures()[i]);
            }

            for (int i = 0; i < 5; i++) {
                data[k++] = String.valueOf(sample.getAreas()[i]);
            }

            csvWriter.writeNext(data);
            k = 0;
        }

        csvWriter.close();
    }
}
