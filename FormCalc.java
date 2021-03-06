import com.sun.javafx.binding.StringFormatter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;


public class FormCalc extends JFrame {      // Наследуя от JFrame мы получаем всю функциональность окна
    JRadioButton rbRub = new JRadioButton();// Радиокнопка рубли
    JRadioButton rbDol = new JRadioButton();// Радиокнопка доллары
    JTextField tSum = new JTextField(10);   // Текстовое поле для ввода суммы
    JButton butCalc = new JButton("Рассчитать");    // Кнопка "Рассчитать"
    JTextArea tOutput = new JTextArea(20, 5);       // Текстовое поле для вывода расчетов
    JComboBox cb;                           // Выбор срока вклада

    // Конструктор по умолчанию
    public FormCalc(){
		super("Депозитный калькулятор");    // Заголовок окна
        int sizeWidth = 640;                // Ширина окна
        int sizeHeight = 400;               // Высота окна
        // Получаетм размер экрана
        Dimension t = Toolkit.getDefaultToolkit().getScreenSize();
        // Считаем координаты центра экрана
        int x = ((t.width - sizeWidth) / 2);
        int y = ((t.height - sizeHeight) / 2);
        // Устаналиваем окно по центру с шириной и высотой sizeWidth, sizeHeight
        setBounds(x, y, sizeWidth, sizeHeight);
        // При закрытии окна завершится и процесс программы
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Инициализируем компоненты на форму
        InitializeComponentForm();
    }

    /**
     * Инициализация компонентов окна
     */
    public void InitializeComponentForm(){
        JPanel panelCheck = new JPanel();               // Панель для 2-ух чекбоксов
        // Последовательное размещение компонентов в панеле
        panelCheck.setLayout(new FlowLayout(SwingConstants.EAST));
        rbRub.setText("Рубли");                         // Устаналиваем текст
        rbRub.setSelected(true);                        // Отмечаем checkBox
        rbRub.setName("Rub");                           // Имя кнопки
        rbDol.setText("Долары");                        // Устаналиваем текст
        rbDol.setName("Dol");                           // Имя кнопки
        // Настриваем слушателя на радиокнопки
        ActionListener aListenerRB = new RadioButtonActionListener();
        rbRub.addActionListener(aListenerRB);           // Для радиокнопки "Рубли"
        rbDol.addActionListener(aListenerRB);           // Для радиокнопки "Доллары"
        panelCheck.add(new JLabel("Выбор валюты: "));   // Добавляем лайбл в панель
        panelCheck.add(rbRub);                          // Добавляем чекБокс рубли
        panelCheck.add(rbDol);                          // Добавляем чекБокс долары
        panelCheck.setAlignmentX(LEFT_ALIGNMENT);       // По левому краю

        JPanel panelSum = new JPanel();                 // Панель для комп. лайбл и тек. пол.
        // Последовательное размещение компонентов в панеле
        panelSum.setLayout(new FlowLayout(SwingConstants.EAST));
        panelSum.add(new JLabel("Введите сумму: "));    // Добавляем лайбл
        panelSum.add(tSum);                             // Добавляем текстовое поле
        panelSum.setAlignmentX(LEFT_ALIGNMENT);         // По левому краю
        tSum.setText("1000");                           // Устаналиваем текст

        // Последовательное размещение компонентов в панеле
        JPanel panelCB = new JPanel(new FlowLayout(SwingConstants.EAST));
        Vector <String> items = new Vector<String>();   // Создаем вектор
        // Заполняем вектор
        items.add("3 месяца"); items.add("6 месяца"); items.add("9 месяца");
        items.add("1 год"); items.add("2 года");
        cb = new JComboBox(items);                      // Заполняем comboBox значениями из вектора
        panelCB.add(new JLabel("Выберите срок вклада"));// Добавляем лайбл
        panelCB.add(cb);                                // Добавляем comboBox
        panelCB.setAlignmentX(LEFT_ALIGNMENT);          // По левому краю

        butCalc.setAlignmentX(LEFT_ALIGNMENT);          // Кнопка расчитать по левому краю
        butCalc.setAlignmentX(TOP_ALIGNMENT);           // Кнопка расчитать привязать к верху
        ActionListener aListener = new ButtonCalcActionListener();
        butCalc.addActionListener(aListener);

        // Настраиваем к текстовому полю скролл (вертикальный и горизонтальный)
        JPanel panelTextArea = new JPanel(new BorderLayout());
        JScrollPane sPanelTextArea = new JScrollPane(tOutput);
        tOutput.setCaretPosition(0);
        panelTextArea.add(sPanelTextArea, BorderLayout.CENTER);
        sPanelTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        sPanelTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        // Создаем менеджер блочного размещения компонентов
        Box box = Box.createVerticalBox();
        box.add(panelCheck);                            // Добавляем панель "Чекбоксы"
        box.add(panelSum);                              // Добавляем панель "Ввод суммы"
        box.add(panelCB);                               // Добавляем панель "Выбор срока вклада"
        box.add(Box.createRigidArea(new Dimension(10, 0)));
        box.add(butCalc);                               // Добавляем кнопку "Рассчитать"
        box.add(Box.createRigidArea(new Dimension(0, 10)));
        box.add(panelTextArea);                         // Устаналиваем "Текстовое поле"
        box.add(Box.createRigidArea(new Dimension(10, 1)));

        setContentPane(box);                            // Устаналиваем панель
        pack();                                         // Минимальная ширина высота окна
        // Установим панелям, текущий размер - максимальным.
        panelCheck.setMaximumSize(new Dimension(panelCheck.getWidth(), panelCheck.getHeight()));
        panelSum.setMaximumSize(new Dimension(panelSum.getWidth(), panelSum.getHeight()));
    }

    public static void main(String[] args) {
        FormCalc app = new FormCalc();    // Создаем экземпляр нашего приложения
        app.setVisible(true);             // С этого момента приложение запущено!
    }

	/**
     * Расчет депозита
     * @param sum сумма вклада
     * @param percentage проценты
     * @param countDay кол-во дней на который берется депозит
     * @return Возвращает конечную сумму по окончанию депозита
     */
    public double CalculateDeposit(double sum, double percentage, int countDay){
        return Math.pow((double)1 + percentage / 100, (double)countDay / 365) * sum;
	}
	
	
	/**
     * Вывод результата расчета депозита для выбранной валюты
     * @param sum сумма вклада на депозит
     */
    public void PrintResultCalc(double sum){
        int countDay = 0;

        tOutput.setText("");
        // Если выбран срок вклада 3 месяца
        if(cb.getSelectedIndex() == 0) countDay = 90;       // 90 дней
        // Иначе если выбран срок вклада 6 месяцев и так далее
        else if(cb.getSelectedIndex() == 1) countDay = 182; // 182 дней
        else if(cb.getSelectedIndex() == 2) countDay = 272; // 272 дней
        else if(cb.getSelectedIndex() == 3) countDay = 365; // 365 дней
        else if(cb.getSelectedIndex() == 4) countDay = 730; // 730 дней


        // Если выбраны рубли
        if(rbRub.isSelected()){
            // Выводим информацию расчетов по рублям для возможных тарифов
            tOutput.append("=======> Рубли <=======\n");
            tOutput.append("Тариф «Победа»  - ставка 8,5% годовых\n");
            tOutput.append("      Начальная сумма - " + String.format("%.3f", sum) + " руб\n");
            tOutput.append("      Итоговая сумма  - " +
                    String.format("%.3f", CalculateDeposit(sum, 8.5, countDay)) + " руб\n");
            // Если выбран срок депозита от 1 года
            if(cb.getSelectedIndex() > 2) {
                tOutput.append("Тариф «Премьер»  - ставка 9,5% годовых\n");
                tOutput.append("      Начальная сумма - " + String.format("%.3f", sum) + " руб\n");
                tOutput.append("      Итоговая сумма  - " +
                        String.format("%.3f", CalculateDeposit(sum, 9.5, countDay)) + " руб\n");
            }
            tOutput.append("Тариф «Пенсионный»  - ставка 11% годовых\n");
            tOutput.append("Тариф только для пенсионеров!\n");
            tOutput.append("      Начальная сумма - " + String.format("%.3f", sum) + " руб\n");
            tOutput.append("      Итоговая сумма  - " +
                    String.format("%.3f", CalculateDeposit(sum, 11, countDay)) + " руб\n");
        }
        // Если выбраны доллары
        else if(rbDol.isSelected()){
            // Выводим информацию расчетов для долларов для возможных тарифов
            tOutput.append("=======> Доллары <=======\n");
            tOutput.append("Тариф «Рантье»  - ставка 2% годовых\n");
            tOutput.append("      Начальная сумма - " + String.format("%.3f", sum) + " $\n");
            tOutput.append("      Итоговая сумма  - " +
                    String.format("%.3f", CalculateDeposit(sum, 2, countDay)) + " $\n");

            // Если выбран срок депозита от 1 года
            if(cb.getSelectedIndex() > 2) {
                tOutput.append("Тариф «Рантье+»  - ставка 2.5% годовых\n");
                tOutput.append("      Начальная сумма - " + String.format("%.3f", sum) + " $\n");
                tOutput.append("      Итоговая сумма  - " +
                        String.format("%.3f", CalculateDeposit(sum, 2.5, countDay)) + " $\n");
            }
        }
    }

    /**
     * Обработчик собтия нажатия на клавишу "Рассчитать"
     */
    public class ButtonCalcActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            double summa = 0;
            // Если сумма депозита введена
            if(!tSum.getText().isEmpty()){
                try{
                    // Считываем сумму из текстового поля
                    summa = Double.parseDouble(tSum.getText());
                    // Выводим рассчитанный результат
                    PrintResultCalc(summa);
                }
                catch (Exception exp){
                    // Выводим сообщение об ошибке в случае неверного ввода
                    JOptionPane.showMessageDialog(null, "Введите число\n" + exp.getMessage());
                }
            }
        }
    }

    /**
     * Обработчик собтия нажатия на радиокнопки "Рубли/Доллары"
     */
    public class RadioButtonActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == rbRub){
                rbDol.setSelected(false);
                rbRub.setSelected(true);
            }
            else if(e.getSource() == rbDol){
                rbRub.setSelected(false);
                rbDol.setSelected(true);
            }
        }
    }
}
