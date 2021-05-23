package ua.goit.jdbc.dao;

import ua.goit.jdbc.DTO.Branch;
import ua.goit.jdbc.DTO.Skill;
import ua.goit.jdbc.DTO.SkillLevel;
import ua.goit.jdbc.config.DatabaseConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SkillDAO extends AbstractDAO<Skill> {

    public SkillDAO(DatabaseConnectionManager connectionManager) {
        super(connectionManager);
    }

    @Override
    protected String getCreateQuery(Skill object) {
        return "INSERT INTO skills (skill_id, branch, skill_level) " +
                "VALUES(?, CAST(? AS language), CAST(? AS level)) RETURNING skill_id;";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE skills SET branch=CAST(? AS language), skill_level=CAST(? AS level) " +
                "WHERE skill_id=? RETURNING skill_id;";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT skill_id, branch, skill_level " +
                "FROM skills WHERE skill_id = ?;";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM skills WHERE skill_id=?;";
    }

    @Override
    protected String getLastIdQuery() {
        return "SELECT max(skill_id) FROM skills;";
    }

    @Override
    protected void setObjectStatement(PreparedStatement statement, Integer id, Skill object) throws SQLException {
        if (id == null) {
            //CREATE
            object.setId(getLastId() + 1);
            statement.setInt(1, object.getId());
            statement.setString(2, object.getBranch().getName());
            statement.setString(3, object.getLevel().getName());
        } else {
            //UPDATE
            statement.setString(1, object.getBranch().getName());
            statement.setString(2, object.getLevel().getName());
            statement.setInt(3, id);
        }
    }

    @Override
    protected Skill convertToObject(ResultSet resultSet) throws SQLException {
        Skill skill = new Skill();
        skill.setId(resultSet.getInt("skill_id"));
        skill.setBranch(Branch.findByName(resultSet.getString("branch")));
        skill.setLevel(SkillLevel.findByName(resultSet.getString("skill_level")));
        return skill;
    }
}
