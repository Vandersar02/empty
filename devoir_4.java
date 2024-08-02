/**
 * @author Lee J. Vandersar St Cyr
 * Cours : JAVA
 * Teacher: Pastor Seige Poteau & John Clayton
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import chris.dj.nativeswing.swtimpl.components.JWebBrowser;

public class devoir_4 {

    public static void main(String[] args) throws ParseException, org.json.simple.parser.ParseException, IOException {
        lstFilms = Utilities.fromJsonArray(Utilities.getJSONElement(URL_NOW_PLAYING));
        mainScreen();
    }

    public static final String api_key = "YOUR_API_KEY";
    public static final String URL_NOW_PLAYING = "https://api.themoviedb.org/3/movie/popular?api_key=" + api_key;
    public static List<Film> lstFilms;

    // ==================================================================
    // some static variables
    public static Film film;
    public static int ind = 0;
    public static JLabel labelImage = new JLabel();
    // ==================================================================

    public static class Film {
        private String backdrop_path, original_language, original_title, overview, poster_path, release_date, title;
        private JSONArray genre_ids;
        private double popularity, vote_average;
        private boolean video, adult;
        private long id, vote_count;

        public Film(boolean adult, String backdrop_path, Long id, String original_language, String original_title,
                String overview, String poster_path, String release_date, String title, JSONArray genre_ids,
                double popularity, boolean video, double vote_average, long vote_count) {
            this.adult = adult;
            this.backdrop_path = backdrop_path;
            this.id = id;
            this.original_language = original_language;
            this.original_title = original_title;
            this.overview = overview;
            this.poster_path = poster_path;
            this.release_date = release_date;
            this.title = title;
            this.genre_ids = genre_ids;
            this.popularity = popularity;
            this.video = video;
            this.vote_average = vote_average;
            this.vote_count = vote_count;
        }

        public boolean isAdult() {
            return adult;
        }

        public String getBackdrop_path() {
            return "https://image.tmdb.org/t/p/w342" + backdrop_path;
        }

        public Long getId() {
            return id;
        }

        public String getOriginal_language() {
            return original_language;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public String getOverview() {
            return overview;
        }

        public String getPoster_path() {
            return "https://image.tmdb.org/t/p/w342" + poster_path;
        }

        public String getRelease_date() {
            return release_date;
        }

        public String getTitle() {
            return title;
        }

        public JSONArray getGenre_ids() {
            return genre_ids;
        }

        public double getPopularity() {
            return popularity;
        }

        public boolean isVideo() {
            return video;
        }

        public double getVote_average() {
            return vote_average;
        }

        public long getVote_count() {
            return vote_count;
        }

    }

    public static class Utilities {
        private static HttpURLConnection conn;
        public static BufferedReader reader;
        public static String line;
        public static StringBuilder responseContent = new StringBuilder();

        public static JSONArray getJSONElement(String URL)
                throws ParseException, org.json.simple.parser.ParseException {
            JSONArray array = null;
            try {
                URL url = new URL(URL);
                conn = (HttpURLConnection) url.openConnection();

                // Request setup
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);// 5000 milliseconds = 5 seconds
                conn.setReadTimeout(5000);

                // Test if the response from the server is successful
                int status = conn.getResponseCode();

                if (status >= 300) {
                    reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                } else {
                    reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                }
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();

                String content = responseContent.toString();

                JSONParser parser = new JSONParser();

                JSONObject json = (JSONObject) parser.parse(content);

                array = (JSONArray) json.get("results");

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return array;
        }

        private static Film fromJsonObject(JSONObject object) {
            boolean adult = (boolean) object.get("adult");
            String backdrop_path = (String) object.get("backdrop_path");
            JSONArray genre_ids = (JSONArray) object.get("genre_ids");
            Long id = (Long) object.get("id");
            String original_language = (String) object.get("original_language");
            String original_title = (String) object.get("original_title");
            String overview = (String) object.get("overview");
            double popularity = (double) object.get("popularity");
            String poster_path = (String) object.get("poster_path");
            String release_date = (String) object.get("release_date");
            String title = (String) object.get("title");
            boolean video = (boolean) object.get("video");
            double vote_average = Double.parseDouble(object.get("vote_average").toString());
            long vote_count = (long) object.get("vote_count");

            return new Film(adult, backdrop_path, id, original_language, original_title,
                    overview, poster_path, release_date, title, genre_ids,
                    popularity, video, vote_average, vote_count);
        }

        public static List<Film> fromJsonArray(JSONArray array) {
            List<Film> listeFilms = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                listeFilms.add(fromJsonObject((JSONObject) array.get(i)));
            }
            return listeFilms;
        }

    }

    public static ImageIcon draw(String url) throws MalformedURLException, IOException {
        BufferedImage image = ImageIO.read(new URL(url));
        ImageIcon icon = new ImageIcon(image);
        return icon;
    }
    // The frames
    // ===================

    public static void mainScreen() throws IOException {
        JFrame frmain = new JFrame();
        frmain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmain.setTitle("MAIN");
        // frmain.setResizable(false);
        frmain.setSize(600, 600);
        // frmain.setForeground(Color.blue);
        frmain.setLocation(100, 100);

        // The first panel to add the Canvas
        JPanel panel = new JPanel();
        film = lstFilms.get(ind);

        labelImage = new JLabel(draw(film.getPoster_path()));
        labelImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    selectedMovie();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                frmain.setVisible(false);
            }
        });
        panel.add(labelImage);

        frmain.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setForeground(new Color(128, 128, 128));

        // The second panel for the buttons
        JPanel panel_1 = new JPanel();
        panel_1.setForeground(new Color(192, 192, 192));
        frmain.getContentPane().add(panel_1, BorderLayout.SOUTH);

        // This button will give the previous movies
        JButton btnPrecedent = new JButton("Previous");
        btnPrecedent.setHorizontalAlignment(SwingConstants.LEFT);
        btnPrecedent.setBounds(100, 300, 60, 70);
        btnPrecedent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ind--;
                if (ind == -1) {
                    ind = lstFilms.size() - 1;
                }

                film = lstFilms.get(ind);
                try {
                    panel.removeAll();
                    panel.setVisible(false);
                    panel.setVisible(true);
                    labelImage = new JLabel(draw(film.getPoster_path()));
                    labelImage.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            try {
                                selectedMovie();
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }

                            frmain.setVisible(false);
                        }
                    });
                    panel.add(labelImage);

                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });
        panel_1.add(btnPrecedent);

        // This button will give the next movies
        JButton btnSuivant = new JButton("Next");
        btnSuivant.setHorizontalAlignment(SwingConstants.LEFT);
        btnSuivant.setBounds(300, 300, 60, 70);
        btnSuivant.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ind++;
                if (ind == lstFilms.size()) {
                    ind = 0;
                }
                film = lstFilms.get(ind);
                try {
                    panel.removeAll();
                    panel.setVisible(false);
                    panel.setVisible(true);
                    labelImage = new JLabel(draw(film.getPoster_path()));
                    labelImage.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            try {
                                selectedMovie();
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }

                            frmain.setVisible(false);
                        }
                    });
                    panel.add(labelImage);

                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });
        panel_1.add(btnSuivant);

        frmain.setVisible(true);
    }

    public static void selectedMovie() throws Exception, Exception {
        Film film = lstFilms.get(ind);
        JFrame frmain = new JFrame();
        frmain.setTitle(film.getTitle());
        frmain.setSize(500, 500);
        frmain.setLocation(100, 100);
        JLabel text = new JLabel();
        String txt = "<html> <h1 align = 'center'>Title: " + film.getTitle() + "</h1>"
                + "Original title: " + film.getOriginal_title() + "<br />"
                + "Original language : " + film.getOriginal_language() + "<br />"
                + "Release Date: " + film.getRelease_date() + "<br />"
                + "Adult: " + film.isAdult() + "<br />"
                + "Popularity: " + film.getPopularity() + "<br />"
                + "Vote Average: " + film.getVote_average() + "<br />"
                + "Vote Count: " + film.getVote_count() + "<br />" + "<br />"
                + "<h1 align = 'center'>  Overview </h1> <br />"
                + film.getOverview() + "<br />";

        text.setText(txt);
        frmain.getContentPane().add(text, BorderLayout.NORTH);
        // ==========================================================================================
        JPanel panel = new JPanel();

        // get back to the main frame
        JButton retour = new JButton("back");
        retour.setVerticalAlignment(SwingConstants.TOP);
        retour.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    frmain.setVisible(false);
                    mainScreen();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        panel.add(retour, BorderLayout.SOUTH);

        frmain.add(panel, BorderLayout.SOUTH);
        // ==========================================================================================
        frmain.setVisible(true);
    }
}
