package axisallies.board;

import axisallies.nations.NationType;
import axisallies.units.Company;
import axisallies.units.Unit;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

import static axisallies.board.TerritoryType.LAND;
import static axisallies.board.TerritoryType.SEA;

@Setter
@Getter
@ToString(exclude = {"neighbours"})
@EqualsAndHashCode(exclude = {"neighbours"})
public class Territory {

    private String territoryName;
    private NationType nationType;
    private TerritoryType territoryType;
    private Set<String> neighbourNames;
    private Set<Territory> neighbours;
    private Company company = new Company();
    private int ipc;

    public void placeCompany(Company otherCompany) {
        company.mergeCompanies(otherCompany);
    }

    public Set<Unit> getCompanyUnits() {
        return company.getUnits();
    }

    public void addUnitToCompany(Unit unit) {
        company.addUnit(unit);
    }

    public void removeUnitFromCompany(Unit unit) {
        company.removeUnit(unit);
    }

    public boolean isSea() {
        return territoryType.equals(SEA);
    }

    public boolean isLand() {
        return territoryType.equals(LAND);
    }

    public boolean containsUnit(Unit unit) {
        return company.containsUnit(unit);
    }

}
