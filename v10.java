import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WebRequester {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Nhập URL
        System.out.print("Nhập link web cần truy cập: ");
        String urlString = scanner.nextLine();

        // Nhập dữ liệu POST
        System.out.print("Nhập dữ liệu POST: ");
        String postData = scanner.nextLine();

        // Nhập số lượng truy cập
        System.out.print("Nhập số lượng truy cập: ");
        int numberOfRequests = scanner.nextInt();
        
        scanner.close();

        try {
            for (int i = 0; i < numberOfRequests; i++) {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                // Gửi dữ liệu POST
                try (OutputStream os = connection.getOutputStream()) {
                    os.write(postData.getBytes());
                    os.flush();
                }

                // Đọc phản hồi từ máy chủ
                int responseCode = connection.getResponseCode();
                System.out.println("Response Code for request " + (i + 1) + ": " + responseCode);

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // In phản hồi
                System.out.println("Response Body for request " + (i + 1) + ": " + response.toString());

                connection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
