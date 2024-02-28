import { Form, Modal } from 'react-bootstrap';
import React, { useState } from 'react';
import Button from 'react-bootstrap/Button';
import { filtersState, isDataNeedsToBeUpdatedState } from '../../state/atoms';
import { useSetRecoilState } from 'recoil';
import { FilterRow } from '../filter/FilterRow';

export const FiltersForm = () => {
  const setFiltersGlobal = useSetRecoilState(filtersState);
  const setIsDataNeedsToBeUpdated = useSetRecoilState(isDataNeedsToBeUpdatedState);
  const [filters, setFilters] = useState({});
  const [show, setShow] = useState(false);

  return (
    <>
      <Button className="button-4" onClick={() => setShow(!show)}>
        Фильтры
      </Button>
      <Modal
        contentClassName="bg-white text-dark p-3"
        id="filtersForm"
        show={show}
        onHide={() => setShow(false)}
        fullscreen={true}>
        <Modal.Header closeButton closeVariant="black">
          <Modal.Title>Назначить фильтры</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <FilterRow filters={filters} setFilters={setFilters} id="id" />
            <FilterRow filters={filters} setFilters={setFilters} id="name" />
            <FilterRow filters={filters} setFilters={setFilters} id="creationDate" type="date" />
            <FilterRow filters={filters} setFilters={setFilters} id="locationFromId" />
            <FilterRow filters={filters} setFilters={setFilters} id="locationFromCoordinatesX" />
            <FilterRow filters={filters} setFilters={setFilters} id="locationFromCoordinatesY" />
            <FilterRow filters={filters} setFilters={setFilters} id="locationFromName" />
            <FilterRow filters={filters} setFilters={setFilters} id="locationToId" />
            <FilterRow filters={filters} setFilters={setFilters} id="locationToCoordinatesX" />
            <FilterRow filters={filters} setFilters={setFilters} id="locationToCoordinatesY" />
            <FilterRow filters={filters} setFilters={setFilters} id="locationToName" />
            <FilterRow filters={filters} setFilters={setFilters} id="distance" />
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button
            variant="text-dark"
            onClick={() => {
              setFilters({});
              setFiltersGlobal({});
            }}>
            Очистить
          </Button>
          <Button
            variant="text-dark"
            onClick={() => {
              setFiltersGlobal(filters);
              setIsDataNeedsToBeUpdated(true);
            }}>
            Применить фильтры
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};
