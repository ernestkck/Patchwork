package comp1140.ass2;

/**
 * Created by jack1 on 18/08/2017.
 */
public class patch {
    public enum Patch {
        A(2, 1, 0),
        B(1, 3, 0),
        C(3, 1, 0),
        D(2, 2, 0),
        E(3, 2, 1),
        F(2, 2, 0),
        G(1, 4, 1),
        H(0, 3, 1),
        I(6, 5, 2),
        J(4, 2, 0),
        K(2, 2, 0),
        L(1, 5, 1),
        M(3, 3, 1),
        N(7, 1, 1),
        O(3, 4, 1),
        P(7, 4, 2),
        Q(3, 6, 2),
        R(2, 1, 0),
        S(4, 6, 2),
        T(5, 4, 2),
        U(2, 3, 0),
        V(5, 3, 1),
        W(10, 3, 2),
        X(5, 5, 2),
        Y(10, 5, 3),
        Z(1, 2, 0),
        a(4, 2, 1),
        b(7, 2, 2),
        c(10, 4, 3),
        d(1, 2, 0),
        e(2, 3, 1),
        f(7, 6, 3),
        g(8, 6, 3),
        h(0, 0, 0);


        private final int buttonCost;
        private final int timeCost;
        private final int buttonIncome;

        Patch(int buttonCost, int timeCost, int buttonIncome) {
            this.buttonCost = buttonCost;
            this.timeCost = timeCost;
            this.buttonIncome = buttonIncome;
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

    }

}
