package axisallies.board;

import axisallies.nations.NationType;
import axisallies.units.Company;
import axisallies.units.Unit;

import java.util.Set;

import static axisallies.board.TerritoryType.LAND;
import static axisallies.board.TerritoryType.SEA;

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

    public String getTerritoryName() {
        return territoryName;
    }

    public void setTerritoryName(String territoryName) {
        this.territoryName = territoryName;
    }

    public NationType getNationType() {
        return nationType;
    }

    public void setNationType(NationType nationType) {
        this.nationType = nationType;
    }

    public TerritoryType getTerritoryType() {
        return territoryType;
    }

    public void setTerritoryType(TerritoryType territoryType) {
        this.territoryType = territoryType;
    }

    public Set<String> getNeighbourNames() {
        return neighbourNames;
    }

    public void setNeighbourNames(Set<String> neighbourNames) {
        this.neighbourNames = neighbourNames;
    }

    public Set<Territory> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(Set<Territory> neighbours) {
        this.neighbours = neighbours;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public int getIpc() {
        return ipc;
    }

    public void setIpc(int ipc) {
        this.ipc = ipc;
    }
}
