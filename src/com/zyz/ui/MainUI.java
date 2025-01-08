package com.zyz.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class MainUI extends JFrame {
    private static final String IMAGE_PATH = "stone-maze-game/src/image";
    private static final int[][] winImageNum = new int[][]{
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };
    // 图片数字二维数组,4*4的矩阵，初始化为0~15
    private int[][] imageNum = new int[][]{
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };
    private int row = 3;
    private int col = 3;
    private int step = 0;


    // 构造函数
    public MainUI() {
        InitFrame();
        // 初始化菜单
        InitMenu();
        //将棋子打乱顺序
        reImageNum();
        // 初始化图片
        InitImage();
        //给键盘绑定事件
        Keyactive();

        setVisible(true);
    }

    private void InitFrame() {
        setTitle("石子迷阵 v1.0 By Zhoey");
        setSize(460, 525);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 在屏幕中间显示
        setLocationRelativeTo(null);
        // 禁用布局管理器
        setLayout(null);
    }

    private void Keyactive() {
        // 使得 JFrame 获取焦点，才能响应键盘事件
        setFocusable(true);
        requestFocusInWindow(); // 获取焦点

        // 绑定键盘事件
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                // 根据键盘事件来处理上下左右键
                switch (keyCode) {
                    case KeyEvent.VK_UP:    // 上箭头
                        MainUI.this.moveImage(Direction.UP);
                        break;
                    case KeyEvent.VK_DOWN:  //下箭头
                        MainUI.this.moveImage(Direction.DOWN);
                        break;
                    case KeyEvent.VK_LEFT:  // 左箭头
                        MainUI.this.moveImage(Direction.LEFT);
                        break;
                    case KeyEvent.VK_RIGHT: // 右箭头
                        MainUI.this.moveImage(Direction.RIGHT);
                        break;
                }
            }
        });

    }

    private void reImageNum() {
        //将数组imgNum打乱顺序，让0块和它周围的块，随机交换100次
        for(int i = 0;i < 100;i++){
            //随机一个0到4的数
            int dir = (int)(Math.random()*4);
            switch (dir){
                case 0:
                    moveImage(Direction.UP);
                    break;
                case 1:
                    moveImage(Direction.DOWN);
                    break;
                case 2:
                    moveImage(Direction.LEFT);
                    break;
                case 3:
                    moveImage(Direction.RIGHT);
                    break;
                default:
                    break;
            }
        }
        step = 0;

//        //找到0的位置
//        OUT:
//        for(int i = 0;i < imageNum.length;i++){
//            for(int j = 0;j < imageNum[i].length;j++){
//                if(imageNum[i][j] == 0){
//                    row = i;
//                    col = j;
//                    break OUT;
//                }
//            }
//        }



    }

    private void moveImage(Direction direction) {
        switch (direction) {
            case UP:
                if(row>=0 && row<3){
                    int temp = imageNum[row][col];
                    imageNum[row][col] = imageNum[row+1][col];
                    imageNum[row+1][col] = temp;
                    row++;
                    step++;
                }
                break;
            case DOWN:
                if(row>0 && row<=3){
                    int temp = imageNum[row][col];
                    imageNum[row][col] = imageNum[row-1][col];
                    imageNum[row-1][col] = temp;
                    row--;
                    step++;
                }
                break;
            case LEFT:
                if(col>=0 && col<3){
                    int temp = imageNum[row][col];
                    imageNum[row][col] = imageNum[row][col+1];
                    imageNum[row][col+1] = temp;
                    col++;
                    step++;
                }
                break;
            case RIGHT:
                if(col>0 && col<=3){
                    int temp = imageNum[row][col];
                    imageNum[row][col] = imageNum[row][col-1];
                    imageNum[row][col-1] = temp;
                    col--;
                    step++;
                }
                break;
        }
        // 重新初始化图片
        InitImage();
    }

    // 菜单初始化
    private void InitMenu() {
        // 菜单名为选项，菜单项名为重玩，退出
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("选项");
        JMenuItem menuItem1 = new JMenuItem("重玩");
        JMenuItem menuItem2 = new JMenuItem("退出");
        JMenuItem menuItem3 = new JMenuItem("作弊");
        menu.add(menuItem1);
        menu.add(menuItem2);
        menu.add(menuItem3);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        // 重玩绑定事件
        menuItem1.addActionListener(e -> {
            step = 0;
            reImageNum();
            InitImage();
            setFocusable(true);  // 恢复键盘事件
            requestFocusInWindow();
        });

        menuItem2.addActionListener(e -> {
            // 弹出提醒，你点击了退出
            JOptionPane.showMessageDialog(this, "你点击了退出", "提示", JOptionPane.INFORMATION_MESSAGE);
            // 关闭窗口
            System.exit(0);
        });

        menuItem3.addActionListener(e -> {
            // 弹出提醒，你点击了作弊
            JOptionPane.showMessageDialog(this, "你点击了作弊", "提示", JOptionPane.INFORMATION_MESSAGE);
            // 将二维数组imageNum赋值为winImageNum
            imageNum = new int[][]{
                    {1, 2, 3, 4},
                    {5, 6, 7, 8},
                    {9, 10, 11, 12},
                    {13, 14, 15, 0}
            };
            row = 3;
            col = 3;
            // 重新初始化图片
            InitImage();
        });
    }

    // 图片初始化
    private void InitImage() {
        getContentPane().removeAll();
    // 显示步数
        JLabel stepLabel = new JLabel("步数：" + step);
        stepLabel.setBounds(10, 15, 100, 30);

    // 设置字体为大一点，并设置为白色
        stepLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));  // 设置字体为 Arial、粗体、大小为 20
        stepLabel.setForeground(Color.WHITE);  // 设置字体颜色为白色

        add(stepLabel);
        //判断游戏是否胜利,判断二维数组imageNum是否等于winImagenum
        if(Arrays.deepEquals(imageNum,winImageNum)){
            //加载胜利图片
            JLabel label = new JLabel(new ImageIcon(IMAGE_PATH + "/win.png"));
            label.setBounds(100, 200, 266, 88);
            add(label);
            //按键监听器失效
            setFocusable(false);
        }else{
            setFocusable(true);  // 恢复键盘事件
            requestFocusInWindow();
        }

        // 根据数组初始化棋子
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                JLabel label = new JLabel();
                // 设置图片标签的大小
                label.setBounds(23 + j * 100, 58 + i * 100, 100, 100);
                // 设置图片标签的图片
                label.setIcon(new ImageIcon(IMAGE_PATH + "/" + imageNum[i][j] + ".png"));
                // 将图片标签添加到窗口中
                add(label);
            }
        }
        // 添加背景图
        JLabel background = new JLabel(new ImageIcon(IMAGE_PATH + "/background.png"));
        background.setBounds(0, 0, 450, 484);
        add(background);
        revalidate();
        repaint();
    }


}
