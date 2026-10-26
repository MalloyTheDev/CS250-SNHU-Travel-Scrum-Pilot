/*
 * CS 250 Module Five: SNHU Travel Slide Show
 * Brendan Malloy
 *
 * The Product Owner moved the focus of the booking tool to detox and wellness
 * travel. The slide show control already worked, so this is a content change.
 * I swapped the five images, rewrote the five descriptions, and updated the
 * window title. The five slide structure, the CardLayout navigation, and the
 * Previous and Next buttons came from the starter project and were left alone.
 *
 * One layout bug in the starter did have to be fixed. The starter added both the
 * description panel and the button panel to BorderLayout.SOUTH, and a BorderLayout
 * only keeps the last component added to a region, so the descriptions never
 * appeared. The new requirement is to show wellness text with each picture, so the
 * text has to be on screen. I grouped the two panels into one footer panel to get
 * both. That is the only structural change I made.
 */

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class SlideShow extends JFrame {

    // JFrame is serializable, so declaring this ID keeps the compiler and Eclipse
    // from flagging the class. It has no effect on how the slide show runs.
    private static final long serialVersionUID = 1L;

    // Two CardLayout objects instead of one. The picture and its caption live in
    // separate panels because they sit in different regions of the frame, so each
    // panel needs its own layout. Stepping both layouts together in goNext and
    // goPrevious is what keeps a destination image matched to its own description.
    private JPanel slidePane;
    private JPanel textPane;
    private JPanel buttonPane;
    private CardLayout card;
    private CardLayout cardText;
    private JButton btnPrev;
    private JButton btnNext;
    private JLabel lblSlide;
    private JLabel lblTextArea;

    /**
     * Create the application.
     */
    public SlideShow() throws HeadlessException {
        initComponent();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initComponent() {
        card = new CardLayout();
        cardText = new CardLayout();
        slidePane = new JPanel();
        textPane = new JPanel();
        buttonPane = new JPanel();
        btnPrev = new JButton();
        btnNext = new JButton();
        lblSlide = new JLabel();
        lblTextArea = new JLabel();

        // Window title updated to the new travel focus. Size and close behavior
        // are unchanged from the starter.
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("SNHU Travel - Top 5 Detox & Wellness Getaways");
        getContentPane().setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Each panel gets its matching CardLayout so only one card shows at a time.
        // The fixed height on the text panel stops the caption area from resizing
        // when one description runs longer than another, which would make the
        // picture jump as the user clicks through the slides.
        slidePane.setLayout(card);
        textPane.setLayout(cardText);
        textPane.setBackground(new Color(242, 247, 243));
        textPane.setPreferredSize(new Dimension(780, 91));

        // Build all five slides in one loop. The loop counter does double duty: it
        // picks the image file Wellness1 through Wellness5 and it picks the matching
        // description from getTextDescription. Loading both from the same index is
        // what guarantees slide 3's picture can never end up with slide 4's text.
        // The cards are added in order, so the CardLayout sequence is the ranked
        // order the Product Owner asked for, 1 through 5.
        for (int i = 1; i <= 5; i++) {
            lblSlide = new JLabel();
            lblSlide.setHorizontalAlignment(JLabel.CENTER);
            lblSlide.setIcon(getResizeIcon(i));

            lblTextArea = new JLabel();
            lblTextArea.setHorizontalAlignment(JLabel.CENTER);
            lblTextArea.setBorder(new EmptyBorder(8, 12, 8, 12));
            lblTextArea.setText(getTextDescription(i));

            slidePane.add(lblSlide, "card" + i);
            textPane.add(lblTextArea, "cardText" + i);
        }

        getContentPane().add(slidePane, BorderLayout.CENTER);
        buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        // Previous button from the starter. CardLayout.previous wraps from the first
        // card to the last on its own, so slide 1 steps back to slide 5.
        btnPrev.setText("Previous");
        btnPrev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goPrevious();
            }
        });
        buttonPane.add(btnPrev);

        // Next button from the starter. CardLayout.next wraps from the last card back
        // to the first, so slide 5 steps forward to slide 1 instead of dead ending.
        btnNext.setText("Next");
        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goNext();
            }
        });
        buttonPane.add(btnNext);

        // The starter put both of these panels in BorderLayout.SOUTH, and that region
        // holds one component, so the second add replaced the first and the captions
        // were never drawn. Nesting them in a footer panel gives the text its own
        // region above the buttons and leaves both visible.
        JPanel footerPane = new JPanel(new BorderLayout());
        footerPane.add(textPane, BorderLayout.CENTER);
        footerPane.add(buttonPane, BorderLayout.SOUTH);
        getContentPane().add(footerPane, BorderLayout.SOUTH);
    }

    /**
     * Step back one slide. Both layouts move together so the picture and the
     * description stay on the same destination.
     */
    private void goPrevious() {
        card.previous(slidePane);
        cardText.previous(textPane);
    }

    /**
     * Step forward one slide. Both layouts move together so the picture and the
     * description stay on the same destination.
     */
    private void goNext() {
        card.next(slidePane);
        cardText.next(textPane);
    }

    /**
     * Load one wellness image and scale it to fit the slide area.
     *
     * getResource reads the file out of the running JAR rather than off a hard coded
     * folder path, which is what lets the exported JAR run on any machine without the
     * project folder next to it. The source images are 1200x650, so scaling them to
     * 750x406 holds the same aspect ratio and keeps every slide the same size. If an
     * image is missing the method throws instead of returning null, because a null
     * icon would quietly render a blank slide and look like a navigation bug.
     */
    private ImageIcon getResizeIcon(int i) {
        URL imageUrl = getClass().getResource("/resources/Wellness" + i + ".jpg");
        if (imageUrl == null) {
            throw new IllegalStateException("Missing wellness image for slide " + i);
        }
        ImageIcon originalImage = new ImageIcon(imageUrl);
        Image scaledImage = originalImage.getImage().getScaledInstance(750, 406, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    /**
     * Return the description for one slide. These are the five detox and wellness
     * vacations that replaced the starter placeholder text. The index passed in is the
     * same index used to load the picture, so the two always line up.
     */
    private String getTextDescription(int i) {
        String text = "";
        if (i == 1) {
            text = caption(1, "Yoga & Mindfulness Retreat",
                "A digital detox with gentle yoga, meditation, and time away from screens.");
        } else if (i == 2) {
            text = caption(2, "Forest Wellness Escape",
                "Recharge with nature walks, quiet trails, and restful time outdoors.");
        } else if (i == 3) {
            text = caption(3, "Natural Hot Springs Getaway",
                "Slow down with a scenic mineral-spring soak and peaceful surroundings.");
        } else if (i == 4) {
            text = caption(4, "Restorative Spa Retreat",
                "Make space for massage, gentle movement, and a slower daily routine.");
        } else if (i == 5) {
            text = caption(5, "Coastal Mindfulness Break",
                "Unplug with sunrise beach walks, fresh sea air, and time to rest.");
        }
        return text;
    }

    /**
     * Build one caption string.
     *
     * JLabel renders basic HTML, so wrapping the text in HTML tags is how I get a
     * bold title on one line and the description on a second line inside a single
     * label. Routing all five captions through this one method keeps the rank number,
     * font sizes, and colors identical from slide to slide, and it means a styling
     * change only has to be made in one place.
     */
    private String caption(int rank, String title, String description) {
        return "<html><div align='center'><font size='5' color='#254D45'><b>#"
            + rank + " " + title + "</b></font><br><font size='3' color='#334C47'>"
            + description + "</font></div></html>";
    }

    /**
     * Launch the application on the Swing event dispatch thread.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                SlideShow ss = new SlideShow();
                ss.setVisible(true);
            }
        });
    }
}
