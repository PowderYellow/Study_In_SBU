package geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * * @Date: 4/8/23 5:51 PM
 */
public class RadialGraphSymmetries {


    boolean areSymmetric(RadialGraph g1, RadialGraph g2) {

        List<RadialGraph> NRG = symmetriesOf(g1);
        for (RadialGraph rg : NRG) {
            if (rg.equals(g2)) {
                return true;
            }
        }
        return false;
    }

    List<RadialGraph> symmetriesOf(RadialGraph g1) {

        List<RadialGraph> rg = new ArrayList<>();
        int lens = g1.vertices.length;
        int deg = 360 / lens;
        int temp = deg;
        for (int i = 0; i < lens; i++, temp += deg) {
            rg.add(g1.rotateBy(temp));
        }
        return rg;
    }
}

