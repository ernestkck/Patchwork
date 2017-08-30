package comp1140.ass2;

/**
 * Created by jack1 on 18/08/2017.
 */
public enum Patch {
        A(2, 1, 0, new boolean[][]{
                {true},
                {true}
        }),
        B(1, 3, 0, new boolean[][]{
                {false, true},
                {true, true}
        }),
        C(3, 1, 0, new boolean[][]{
                {false, true},
                {true, true}
        }),
        D(2, 2, 0, new boolean[][]{
                {true},
                {true},
                {true}
        }),
        E(3, 2, 1, new boolean[][]{
                {false, true},
                {true, true},
                {true, false}
        }),
        F(2, 2, 0, new boolean[][]{
                {true, false},
                {true, true},
                {true, true}
        }),
        G(1, 4, 1, new boolean[][]{
                {false, false, true, false, false},
                {true,  true, true, true, true},
                {false, false, true, false, false}
        }),
        H(0, 3, 1, new boolean[][]{
                {false, true, false},
                {true, true, true},
                {false, true, false},
                {false, true, false}
        }),
        I(6, 5, 2, new boolean[][]{
                {true, true},
                {true, true}
        }),
        J(4, 2, 0, new boolean[][]{
                {true, false},
                {true, true},
                {true, true},
                {false, true}
        }),
        K(2, 2, 0, new boolean[][]{
                {false, true},
                {true, true},
                {false, true}
        }),
        L(1, 5, 1, new boolean[][]{
                {true, true},
                {false, true},
                {false, true},
                {true, true}
        }),
        M(3, 3, 1, new boolean[][]{
                {true},
                {true},
                {true},
                {true}
        }),
        N(7, 1, 1, new boolean[][]{
                {true, true, true, true, true}
        }),
        O(3, 4, 1, new boolean[][]{
                {true, false},
                {true, false},
                {true, true},
                {true, false}
        }),
        P(7, 4, 2, new boolean[][]{
                {true, false},
                {true, true},
                {true, true},
                {true, false}
        }),
        Q(3, 6, 2, new boolean[][]{
                {false, true, false},
                {true, true, true},
                {true, false, true}
        }),
        R(2, 1, 0, new boolean[][]{
                {false, true, false},
                {false, true, true},
                {true, true, false},
                {false, true, false}
        }),
        S(4, 6, 2, new boolean[][]{
                {false, true},
                {false, true},
                {true, true}
        }),
        T(5, 4, 2, new boolean[][]{
                {false, true, false},
                {true, true, true},
                {false, true, false}
        }),
        U(2, 3, 0, new boolean[][]{
                {true, false, true},
                {true, true, true},
                {true, false, true}
        }),
        V(5, 3, 1, new boolean[][]{
                {false, true, false},
                {true, true, true},
                {true, true, true},
                {false, true, false}
        }),
        W(10, 3, 2, new boolean[][]{
                {false, true},
                {false, true},
                {false, true},
                {true, true}
        }),
        X(5, 5, 2, new boolean[][]{
                {false, true, false},
                {false, true, false},
                {true, true, true}
        }),
        Y(10, 5, 3, new boolean[][]{
                {true, true},
                {true, true},
                {false, true},
                {false, true}
        }),
        Z(1, 2, 0, new boolean[][]{
                {true, true, false},
                {false, true, false},
                {false, true, false},
                {false, true, true}
        }),
        a(4, 2, 1, new boolean[][]{
                {true, false},
                {true, false},
                {true, true}
        }),
        b(7, 2, 2, new boolean[][]{
                {false, true, false},
                {false, true, false},
                {false, true, false},
                {true, true, true}
        }),
        c(10, 4, 3, new boolean[][]{
                {true, false, false},
                {true, true, false},
                {false, true, true}
        }),
        d(1, 2, 0, new boolean[][]{
                {true, false, true},
                {true, true, true}
        }),
        e(2, 3, 1, new boolean[][]{
                {false, true},
                {false, true},
                {true, true},
                {true, false}
        }),
        f(7, 6, 3, new boolean[][]{
                {false, true, true},
                {true, true, false}
        }),
        g(8, 6, 3, new boolean[][]{
                {false, true, true},
                {false, true, true},
                {true, true, false}
        }),
        h(0, 0, 0, new boolean[][] {{true}});


        private final int buttonCost;
        private final int timeCost;
        private final int buttonIncome;
        private final boolean[][] locationGrid;

        Patch(int buttonCost, int timeCost, int buttonIncome, boolean[][] locationGrid) {
            this.buttonCost = buttonCost;
            this.timeCost = timeCost;
            this.buttonIncome = buttonIncome;
            this.locationGrid = locationGrid;
        }

        public char getChar(){
            return this.name().charAt(0);
        }
        public int getButtonCost(){
            return buttonCost;
        }
        public int getTimeCost(){
            return timeCost;
        }
        public int getButtonIncome(){
            return buttonIncome;
        }

        public boolean[][] getLocationGrid() {
                return locationGrid;
        }
}
