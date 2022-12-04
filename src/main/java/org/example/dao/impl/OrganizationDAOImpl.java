package org.example.dao.impl;


import com.google.inject.Inject;
import generated.tables.records.OrganizationRecord;
import org.example.config.DBCredentials;
import org.example.dao.OrganizationDAO;
import org.example.mapper.OrganizationMapper;
import org.example.pojo.OrganizationDTO;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static generated.Tables.ORGANIZATION;

public class OrganizationDAOImpl implements OrganizationDAO {


    private final DBCredentials dbCredentials;

    private final OrganizationMapper organizationMapper;

    @Inject
    public OrganizationDAOImpl(DBCredentials dbCredentials, OrganizationMapper organizationMapper) {
        this.dbCredentials = dbCredentials;
        this.organizationMapper = organizationMapper;
    }

    @Override
    public Collection<OrganizationDTO> getAll() {
        try (Connection conn = DriverManager.getConnection(dbCredentials.getCONNECTION() + dbCredentials.getDB_NAME(),
                dbCredentials.getUSERNAME(), dbCredentials.getPASSWORD())) {
            final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);

            return context
                    .selectFrom(ORGANIZATION)
                    .fetchStream()
                    .map(organizationMapper::toEntity)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<OrganizationDTO> getById(@NotNull Long id) {
        try (Connection conn = DriverManager.getConnection(dbCredentials.getCONNECTION() + dbCredentials.getDB_NAME(),
                dbCredentials.getUSERNAME(), dbCredentials.getPASSWORD())) {
            final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);

            final @NotNull Optional<OrganizationRecord> record = Optional.ofNullable(context
                    .selectFrom(ORGANIZATION)
                    .where(ORGANIZATION.ID.eq(id))
                    .fetchOne());
            return record.map(organizationMapper::toEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(@NotNull Long id) {
        try (Connection conn = DriverManager.getConnection(dbCredentials.getCONNECTION() + dbCredentials.getDB_NAME(),
                dbCredentials.getUSERNAME(), dbCredentials.getPASSWORD())) {
            final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);

            context
                    .deleteFrom(ORGANIZATION)
                    .where(ORGANIZATION.ID.eq(id))
                    .execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public OrganizationDTO update(@NotNull OrganizationDTO value) {
        if (value.getId() == null || getById(value.getId()).isEmpty()) {
            throw new IllegalArgumentException("Row with this id is not existing");
        }
        try (Connection conn = DriverManager.getConnection(dbCredentials.getCONNECTION() + dbCredentials.getDB_NAME(),
                dbCredentials.getUSERNAME(), dbCredentials.getPASSWORD())) {
            final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);

            context
                    .update(ORGANIZATION)
                    .set(ORGANIZATION.NAME, value.getName())
                    .where(ORGANIZATION.ID.eq(value.getId()))
                    .execute();
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public OrganizationDTO save(@NotNull OrganizationDTO value) {
        try (Connection conn = DriverManager.getConnection(dbCredentials.getCONNECTION() + dbCredentials.getDB_NAME(),
                dbCredentials.getUSERNAME(), dbCredentials.getPASSWORD())) {
            final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);

            return organizationMapper.toEntity(Objects.requireNonNull(context.
                    insertInto(ORGANIZATION, ORGANIZATION.NAME)
                    .values(value.getName())
                    .returning()
                    .fetchOne()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<OrganizationDTO> getByName(String name) {
        try (Connection conn = DriverManager.getConnection(dbCredentials.getCONNECTION() + dbCredentials.getDB_NAME(),
                dbCredentials.getUSERNAME(), dbCredentials.getPASSWORD())) {
            final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);

            final @NotNull Optional<OrganizationRecord> record = Optional.ofNullable(context
                    .selectFrom(ORGANIZATION)
                    .where(ORGANIZATION.NAME.eq(name))
                    .fetchOne());
            return record.map(organizationMapper::toEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
