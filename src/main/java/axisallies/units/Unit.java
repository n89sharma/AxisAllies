package axisallies.units;

import axisallies.board.Territory;
import axisallies.nations.NationType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Unit {

    private Territory territory;
    private NationType nationType;
    private UnitType unitType;
    private String territoryName;
}
