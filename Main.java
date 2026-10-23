package p1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main extends JFrame {
    private static final Color BG = new Color(30, 30, 46), PANEL = new Color(49, 50, 68);
    private final Queue<GameCharacter> queue = new LinkedList<>(); // ADT: turn order
    private final JPanel cards = new JPanel(new GridLayout(0, 1, 0, 8));
    private final JTextField nameField = new JTextField(12);
    private final Arena arena = new Arena();
    private final JComboBox<String> typeBox = new JComboBox<>(new String[]{"Warrior", "Mage"});
    private final JTextArea log = new JTextArea();

    public Main() {
        super("Game Character Arena");
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout(12, 12));

        // Input panel
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 12));
        top.setBackground(PANEL);
        top.add(label("Name:")); top.add(nameField);
        top.add(label("Type:")); top.add(typeBox);
        top.add(button("Add Character", new Color(166, 227, 161), e -> addCharacter()));
        add(top, BorderLayout.NORTH);

        // Character cards with HP / mana bars (in queue order)
        cards.setBackground(BG);
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setBackground(BG);
        wrap.add(cards, BorderLayout.NORTH);
        JScrollPane left = new JScrollPane(wrap);
        left.setBorder(BorderFactory.createTitledBorder(null, "Turn Order (Queue)", 0, 0, null, Color.WHITE));
        left.setPreferredSize(new Dimension(400, 0));
        left.setOpaque(false);
        add(left, BorderLayout.WEST);

        // Battle log
        log.setEditable(false); log.setBackground(PANEL); log.setForeground(new Color(205, 214, 244));
        log.setFont(new Font("Monospaced", Font.PLAIN, 15));
        JScrollPane logScroll = new JScrollPane(log);
        logScroll.setPreferredSize(new Dimension(0, 210));   // log sits at the bottom of the arena
        arena.add(logScroll, BorderLayout.SOUTH);
        add(arena, BorderLayout.CENTER);

        // Action buttons
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 12));
        bottom.setBackground(PANEL);
        bottom.add(button("Attack", new Color(243, 139, 168), e -> act(false)));
        bottom.add(button("Special Move", new Color(203, 166, 247), e -> act(true)));
        bottom.add(button("Remove Front", new Color(250, 179, 135), e -> removeFront()));
        add(bottom, BorderLayout.SOUTH);

        setJMenuBar(buildMenu());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1150, 700);
        setLocationRelativeTo(null);
    }

    private void addCharacter() {
        String name = nameField.getText().trim();
        if (!name.matches("[A-Za-z0-9 ]{1,12}")) {
            error("Name must be 1-12 letters, numbers, or spaces."); return;
        }
        for (GameCharacter c : queue)
            if (c.getName().equalsIgnoreCase(name)) { error("That name already exists."); return; }

        queue.offer(typeBox.getSelectedItem().equals("Warrior") ? new Warrior(name) : new Mage(name));
        log.append("Added " + name + " to the arena.\n");
        nameField.setText("");
        refresh();
    }

    private void act(boolean special) {
        if (queue.size() < 2) { error("Add at least 2 characters to battle!"); return; }
        GameCharacter c = queue.peek();
        if (!c.canUse(special)) { error(c.getName() + " doesn't have enough mana!"); return; }

        queue.poll();                                   // front of the queue takes the turn
        int dmg = special ? c.specialMove() : c.attack();
        List<GameCharacter> targets = c.hitsAll(special) ? new ArrayList<>(queue) : List.of(queue.peek());
        for (GameCharacter t : targets) t.takeDamage(dmg);

        log.append(c.getName() + " uses " + c.moveName(special) + " on "
                + (c.hitsAll(special) ? "everyone" : targets.get(0).getName()) + " for " + dmg + " damage!\n");
        for (GameCharacter t : targets)
            if (!t.isAlive()) { queue.remove(t); log.append(t.getName() + " was defeated!\n"); }

        c.endTurn();                                    // Mage regenerates mana
        queue.offer(c);                                 // then goes to the back
        if (queue.size() == 1) log.append("Winner: " + c.getName() + "!\n");
        refresh();
    }

    private void removeFront() {
        GameCharacter c = queue.poll();
        if (c == null) { error("The queue is empty."); return; }
        log.append(c.getName() + " left the arena.\n");
        refresh();
    }

    private void refresh() {
        cards.removeAll();
        for (GameCharacter c : queue) {
            boolean turn = cards.getComponentCount() == 0;
            JPanel info = new JPanel(new GridLayout(0, 1, 2, 2));
            info.setOpaque(false);
            info.add(label((turn ? "▶ " : "") + c.showName()));
            info.add(bar("HP", c.getHp(), GameCharacter.MAX_HP, new Color(166, 227, 161)));
            if (c.getMaxMana() > 0) info.add(bar("Mana", c.getMana(), c.getMaxMana(), new Color(137, 180, 250)));

            JPanel p = new JPanel(new BorderLayout(8, 0));
            p.setBackground(turn ? new Color(69, 71, 90) : PANEL);
            p.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
            p.add(info, BorderLayout.CENTER);
            cards.add(p);
        }
        cards.revalidate();
        cards.repaint();
        arena.repaint();
    }

    private JProgressBar bar(String text, int value, int max, Color color) {
        JProgressBar b = new JProgressBar(0, max);
        b.setValue(value); b.setStringPainted(true);
        b.setString(text + " " + value + "/" + max);
        b.setForeground(color);
        return b;
    }


    // Plain arena: the two fighters at the front of the queue, with the battle log below
    private class Arena extends JPanel {
        Arena() { setLayout(new BorderLayout()); setBackground(BG); }

        @Override protected void paintComponent(Graphics g0) {
            super.paintComponent(g0);
            Graphics2D g = (Graphics2D) g0;
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight();
            int logH = getComponentCount() > 0 ? getComponent(0).getHeight() : 0;
            int y = (h - logH) / 2;

            g.setFont(new Font("SansSerif", Font.BOLD, 20));
            g.setColor(Color.WHITE);
            int i = 0;
            for (GameCharacter c : queue) {
                if (i > 1) break;
                String s = c.showName();
                int cx = i == 0 ? w / 4 : 3 * w / 4;
                g.drawString(s, cx - g.getFontMetrics().stringWidth(s) / 2, y);
                i++;
            }
        }
    }


    private void error(String msg) { JOptionPane.showMessageDialog(this, msg, "Invalid Input", JOptionPane.WARNING_MESSAGE); }

    private JLabel label(String t) { JLabel l = new JLabel(t); l.setForeground(Color.WHITE); return l; }

    private JButton button(String text, Color color, java.awt.event.ActionListener a) {
        JButton b = new JButton(text);
        b.setBackground(color); b.setForeground(Color.BLACK); b.setFocusPainted(false);
        b.addActionListener(a);
        return b;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }

    private JMenuBar buildMenu() {
        JMenuBar bar = new JMenuBar();

        JMenu game = new JMenu("Game");
        JMenuItem newGame = new JMenuItem("New Game");
        newGame.setAccelerator(KeyStroke.getKeyStroke("control N"));
        newGame.addActionListener(e -> {
            queue.clear();
            log.setText("");
            refresh();
        });
        JMenuItem clearLog = new JMenuItem("Clear Log");
        clearLog.addActionListener(e -> log.setText(""));
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Exit the game?", "Exit",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) System.exit(0);
        });
        game.add(newGame); game.add(clearLog); game.addSeparator(); game.add(exit);

        JMenu help = new JMenu("Help");
        JMenuItem how = new JMenuItem("How to Play");
        how.addActionListener(e -> JOptionPane.showMessageDialog(this,
            "1. Type a name, pick Warrior or Mage, click Add Character.\n"
          + "2. Add at least 2 characters.\n"
          + "3. Attack or Special Move: the front character hits the next one.\n"
          + "4. Mage spells cost mana; mana regenerates each turn.\n"
          + "5. The last character standing wins!",
            "How to Play", JOptionPane.INFORMATION_MESSAGE));
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(e -> JOptionPane.showMessageDialog(this,
            "Game Character Arena\nAbstraction, Queue ADT, Swing GUI, User Input",
            "About", JOptionPane.INFORMATION_MESSAGE));
        help.add(how); help.add(about);

        bar.add(game); bar.add(help);
        return bar;
    }
}