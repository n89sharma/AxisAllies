package axisalliestests;

import static axisallies.nations.NationType.GERMANY;
import static axisallies.units.Company.buildCompany;
import static axisallies.units.UnitType.BOMBER;
import static axisallies.units.UnitType.FIGHTER;
import static axisallies.units.UnitType.INFANTRY;
import static axisallies.units.UnitType.TANK;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import axisallies.gameplay.BattleStrip;

public class BattleStripTest {

    private BattleStrip battleStrip = new BattleStrip();

    @Test
    public void testAttackStackCreation() {

        battleStrip.createAttackerStack(buildCompany(GERMANY, FIGHTER, TANK, TANK, INFANTRY, BOMBER));
        assertThat(battleStrip.getUnitsPerAttackValue().get(1)).isEqualTo(1);;
        assertThat(battleStrip.getUnitsPerAttackValue().get(2)).isNull();
        assertThat(battleStrip.getUnitsPerAttackValue().get(3)).isEqualTo(3);
        assertThat(battleStrip.getUnitsPerAttackValue().get(4)).isEqualTo(1);
    }
}