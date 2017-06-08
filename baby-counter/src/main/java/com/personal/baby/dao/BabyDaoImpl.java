package com.personal.baby.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.personal.baby.model.Baby;

@Repository
public class BabyDaoImpl implements BabyDao {

	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public int update(Baby b) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", b.getName());
		params.put("numPoo", b.getNumPoo());
		params.put("numCry", b.getNumCry());
		params.put("id", b.getId());

		String sql = "UPDATE babies SET name=:name, numPoo=:numPoo, numCry=:numCry WHERE id=:id";

		return namedParameterJdbcTemplate.update(
				sql,
				params);
	}

	@Override
	public Baby findByName(String name) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);

		String sql = "SELECT * FROM babies WHERE name=:name";

		return namedParameterJdbcTemplate.queryForObject(
				sql,
				params,
				new BabyMapper());


	}

	@Override
	public List<Baby> findAll() {
		Map<String, Object> params = new HashMap<String, Object>();

		String sql = "SELECT * FROM babies";

		List<Baby> result = namedParameterJdbcTemplate.query(sql, params, new BabyMapper());

		return result;
	}

	private static final class BabyMapper implements RowMapper<Baby> {

		public Baby mapRow(ResultSet rs, int rowNum) throws SQLException {
			Baby baby = new Baby();
			baby.setId(rs.getInt("id"));
			baby.setName(rs.getString("name"));
			baby.setNumPoo(rs.getInt("numPoo"));
			baby.setNumCry(rs.getInt("numCry"));
			return baby;
		}
	}

}
