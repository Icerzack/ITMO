import set from 'lodash.set';
import { Form, InputGroup } from 'react-bootstrap';
import get from 'lodash.get';
import React from 'react';
import { useRecoilState } from 'recoil';
import { filtersState } from '../../state/atoms';

export const FilterEquality = ({ filters, setFilters, id, type = 'text' }) => {
  const [filtersGlobal, setFiltersGlobal] = useRecoilState(filtersState);

  const change = (event) => {
    const newFilters = JSON.parse(JSON.stringify(filters));
    set(newFilters, id, event.target.value);
    setFilters(newFilters);

    const newFiltersGlobal = JSON.parse(JSON.stringify(filtersGlobal));
    set(newFiltersGlobal, id, '');
    setFiltersGlobal(newFiltersGlobal);
  };

  return (
    <InputGroup className="mb-3">
      <InputGroup.Text>{id.firstLetterToUppercase()}</InputGroup.Text>
      <Form.Control
        type={type}
        onChange={change}
        className={get(filtersGlobal, id, '').length ? 'bg-warning' : ''}
        value={get(filters, id, '')}
      />
    </InputGroup>
  );
};
