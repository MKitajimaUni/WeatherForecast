package org.example;

import org.example.DataParser.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;

public class UI {
    private WeatherDataParser parser;
    private JFrame jframe;
    private JScrollPane weatherPane;
    private JLabel title;
    private JButton searchButton;
    private JButton daysButton;
    private JButton hoursButton;
    private String currentLocation;

    public UI() {
        currentLocation = "Salzburg"; // Default location
    }

    public void start() throws IOException {
        jframe = new JFrame("Weather");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(600, 400);

        title = new JLabel();
        title.setBounds(30, 10, 500, 20);
        jframe.add(title);

        show12hWeatherInfo(currentLocation);

        jframe.setLayout(null);
        jframe.setVisible(true);
    }

    public void show12hWeatherInfo(String location) throws IOException {
        currentLocation = location; // Update current location

        parser = new Hours12WeatherDataParser();
        Map<String, WeatherInfo> data = parser.getWeatherData(parser.parseWeatherData(location));
        String[] columnName = {"Time in the location", "Weather", "Temperature", "Humidity"};
        Object[][] columnData = new Object[12][columnName.length];

        title.setText("  Weather Forecast of " + location + " next 12 hours");
        title.setSize(title.getPreferredSize());
        jframe.add(title);

        int i = 0;
        for (Map.Entry<String, WeatherInfo> entry : data.entrySet()) {
            columnData[i][0] = entry.getKey();
            columnData[i][1] = entry.getValue().getWeatherCode().toString();
            columnData[i][2] = (int) entry.getValue().getTemperature() + "°";
            columnData[i][3] = (int) entry.getValue().getHumidity() + "%";
            i++;
        }

        DefaultTableModel model = new DefaultTableModel(columnData, columnName) {
        };
        JTable table = new JTable(model);
        JScrollPane newWeatherPane = new JScrollPane(table);
        newWeatherPane.setBounds(30, 30, newWeatherPane.getWidth(), newWeatherPane.getHeight());

        if (weatherPane != null)
            jframe.remove(weatherPane);

        weatherPane = newWeatherPane;
        jframe.add(weatherPane);

        searchButton = getSearchButton();
        jframe.add(searchButton);
        daysButton = get5dWButton();
        jframe.add(daysButton);
        hoursButton = get12hWButton();
        jframe.add(hoursButton);
    }

    public void show5dWeatherInfo(String location) throws IOException {
        currentLocation = location; // Update current location

        parser = new Days5WeatherDataParser();
        Map<String, WeatherInfo> data = parser.getWeatherData(parser.parseWeatherData(location));
        String[] columnName = {"Date", "Weather", "Temperature", "Rain Probability"};
        Object[][] columnData = new Object[5][columnName.length];

        title.setText("  Weather Forecast of " + location + " next 5 Days");
        title.setSize(title.getPreferredSize());
        jframe.add(title);

        int i = 0;
        for (Map.Entry<String, WeatherInfo> entry : data.entrySet()) {
            columnData[i][0] = entry.getKey();
            columnData[i][1] = entry.getValue().getWeatherCode().toString();
            columnData[i][2] = (int) entry.getValue().getTemperature() + "°";
            columnData[i][3] = entry.getValue().getRainProbability() + "%";
            i++;
        }

        DefaultTableModel model = new DefaultTableModel(columnData, columnName) {
        };
        JTable table = new JTable(model);
        JScrollPane newWeatherPane = new JScrollPane(table);
        newWeatherPane.setBounds(30, 30, newWeatherPane.getWidth(), newWeatherPane.getHeight());

        if (weatherPane != null)
            jframe.remove(weatherPane);

        weatherPane = newWeatherPane;
        jframe.add(weatherPane);

        searchButton = getSearchButton();
        jframe.add(searchButton);
        daysButton = get5dWButton();
        jframe.add(daysButton);
        hoursButton = get12hWButton();
        jframe.add(hoursButton);
    }

    private JButton getSearchButton() {
        return createButton("Search", 30, 300, e -> {
            String newLocation = JOptionPane.showInputDialog(jframe, "Enter Location:", null);
            if (newLocation != null && !newLocation.trim().isEmpty()) {
                try {
                    show12hWeatherInfo(newLocation);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(jframe, "Input is invalid", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private JButton get5dWButton() {
        return createButton("5 Days", 300, 300, e -> {
            try {
                show5dWeatherInfo(currentLocation);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(jframe, "Unexpected error", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private JButton get12hWButton() {
        return createButton("12 Hours", 400, 300, e -> {
            try {
                show12hWeatherInfo(currentLocation);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(jframe, "Unexpected error", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private JButton createButton(String text, int x, int y, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        button.setSize(button.getPreferredSize());
        button.setLocation(x, y);
        return button;
    }
}
