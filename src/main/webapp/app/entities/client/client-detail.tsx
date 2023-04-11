import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './client.reducer';

export const ClientDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const clientEntity = useAppSelector(state => state.client.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="clientDetailsHeading">
          <Translate contentKey="coopCycleApp.client.detail.title">Client</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{clientEntity.id}</dd>
          <dt>
            <span id="idClient">
              <Translate contentKey="coopCycleApp.client.idClient">Id Client</Translate>
            </span>
          </dt>
          <dd>{clientEntity.idClient}</dd>
          <dt>
            <span id="prenomClient">
              <Translate contentKey="coopCycleApp.client.prenomClient">Prenom Client</Translate>
            </span>
          </dt>
          <dd>{clientEntity.prenomClient}</dd>
          <dt>
            <span id="nomClient">
              <Translate contentKey="coopCycleApp.client.nomClient">Nom Client</Translate>
            </span>
          </dt>
          <dd>{clientEntity.nomClient}</dd>
          <dt>
            <span id="adresseClient">
              <Translate contentKey="coopCycleApp.client.adresseClient">Adresse Client</Translate>
            </span>
          </dt>
          <dd>{clientEntity.adresseClient}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="coopCycleApp.client.email">Email</Translate>
            </span>
          </dt>
          <dd>{clientEntity.email}</dd>
          <dt>
            <span id="telClient">
              <Translate contentKey="coopCycleApp.client.telClient">Tel Client</Translate>
            </span>
          </dt>
          <dd>{clientEntity.telClient}</dd>
        </dl>
        <Button tag={Link} to="/client" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/client/${clientEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClientDetail;
