package comp1140.ass2;

/**
 * Created by jack1 on 18/08/2017.
 */
public enum Patch {
        A(2, 1, 0, new Boolean[][]{
                {true, false},
                {true, false}
        }),
        B(1, 3, 0, new Boolean[][]{
                {false, true},
                {true, true}
        }),
        C(3, 1, 0, new Boolean[][]{
                {false, true},
                {true, true}
        }),
        D(2, 2, 0, new Boolean[][]{
                {true, false, false},
                {true, false, false},
                {true, false, false}
        }),
        E(3, 2, 1, new Boolean[][]{
                {false, true, false},
                {true, true, false},
                {true, false, false}
        }),
        F(2, 2, 0, new Boolean[][]{
                {true, false, false},
                {true, true, false},
                {true, true, false}
        }),
        G(1, 4, 1, new Boolean[][]{
                {false, false, true, false, false},
                {true, true, true, true, true},
                {false, false, true, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false}
        }),
        H(0, 3, 1, new Boolean[][]{
                {false, true, false, false},
                {true, true, true, false},
                {false, true, false, false},
                {false, true, false, false}
        }),
        I(6, 5, 2, new Boolean[][]{
                {true, true},
                {true, true}
        }),
        J(4, 2, 0, new Boolean[][]{
                {true, false, false, false},
                {true, true, false, false},
                {true, true, false, false},
                {false, true, false, false}
        }),
        K(2, 2, 0, new Boolean[][]{
                {true, false, false},
                {true, true, false},
                {true, true, false}
        }),
        L(1, 5, 1, new Boolean[][]{
                {true, true, false},
                {false, true, false},
                {true, true, false}
        }),
        M(3, 3, 1, new Boolean[][]{
                {true, false, false, false},
                {true, false, false, false},
                {true, false, false, false},
                {true, false, false, false}
        }),
        N(7, 1, 1, new Boolean[][]{
                {false, false, false, false},
                {false, false, false, false},
                {false, false, false, false},
                {true, true, true, true}
        }),
        O(3, 4, 1, new Boolean[][]{
                {true, false, false, false},
                {true, false, false, false},
                {true, true, false, false},
                {true, false, false, false}
        }),
        P(7, 4, 2, new Boolean[][]{
                {true, false, false, false},
                {true, true, false, false},
                {true, true, false, false},
                {true, false, false, false}
        }),
        Q(3, 6, 2, new Boolean[][]{
                {false, true, false},
                {true, true, true},
                {true, false, true}
        }),
        R(2, 1, 0, new Boolean[][]{
                {false, true, false, false},
                {false, true, true, false},
                {true, true, false, false},
                {false, true, false, false}
        }),
        S(4, 6, 2, new Boolean[][]{
                {false, true, false},
                {false, true, false},
                {true, true, false}
        }),
        T(5, 4, 2, new Boolean[][]{
                {false, true, false},
                {true, true, true},
                {false, true, false}
        }),
        U(2, 3, 0, new Boolean[][]{
                {true, false, true},
                {true, true, true},
                {true, false, true}
        }),
        V(5, 3, 1, new Boolean[][]{
                {false, true, false, false},
                {true, true, true, false},
                {true, true, true, false},
                {false, true, false, false}
        }),
        W(10, 3, 2, new Boolean[][]{
                {false, true, false, false},
                {false, true, false, false},
                {false, true, false, false},
                {true, true, false, false}
        }),
        X(5, 5, 2, new Boolean[][]{
                {false, true, false},
                {false, true, false},
                {true, true, true}
        }),
        Y(10, 5, 3, new Boolean[][]{
                {true, true, false, false},
                {true, true, false, false},
                {false, true, false, false},
                {false, true, false, false}
        }),
        Z(1, 2, 0, new Boolean[][]{
                {true, true, false, false},
                {false, true, false, false},
                {false, true, false, false},
                {false, true, true, false}
        }),
        a(4, 2, 1, new Boolean[][]{
                {true, false, false, false},
                {true, false, false, false},
                {true, false, false, false},
                {true, true, false, false}
        }),
        b(7, 2, 2, new Boolean[][]{
                {false, true, false, false},
                {false, true, false, false},
                {false, true, false, false},
                {true, true, true, false}
        }),
        c(10, 4, 3, new Boolean[][]{
                {true, false, false},
                {true, true, false},
                {false, true, true}
        }),
        d(1, 2, 0, new Boolean[][]{
                {false, false, false},
                {true, false, true},
                {true, true, true}
        }),
        e(2, 3, 1, new Boolean[][]{
                {false, true, false, false},
                {false, true, false, false},
                {true, true, false, false},
                {true, false, false, false}
        }),
        f(7, 6, 3, new Boolean[][]{
                {false, true, true},
                {true, true, false},
                {false, false, false}
        }),
        g(8, 6, 3, new Boolean[][]{
                {false, true, true},
                {false, true, true},
                {true, true, false}
        }),
        h(0, 0, 0, new Boolean[][] {{true}});


        private final int buttonCost;
        private final int timeCost;
        private final int buttonIncome;
        private final Boolean[][] locationGrid; // I've made each of these 2d arrays squares, this will make rotating easier later.

        Patch(int buttonCost, int timeCost, int buttonIncome, Boolean[][] locationGrid) {
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

        public Boolean[][] getLocationGrid() {
                return locationGrid;
        }
}
