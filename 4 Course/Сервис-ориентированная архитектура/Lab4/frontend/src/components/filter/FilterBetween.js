import { Form, InputGroup } from 'react-bootstrap';
import React from 'react';
import set from 'lodash.set';
import get from 'lodash.get';
import { useRecoilState } from 'recoil';
import { filtersState } from '../../state/atoms';

export const FilterBetween = ({ filters, setFilters, id, type = 'number', step = 'any' }) => {
  const [filtersGlobal, setFiltersGlobal] = useRecoilState(filtersState);
  const change = (event) => {
    const newFilters = JSON.parse(JSON.stringify(filters));
    set(newFilters, id + '.' + event.target.id, event.target.value);
    setFilters(newFilters);

    const newFiltersGlobal = JSON.parse(JSON.stringify(filtersGlobal));
    set(newFiltersGlobal, id, {});
    setFiltersGlobal(newFiltersGlobal);
  };

  return (
    <InputGroup className="mb-3">
      <InputGroup.Text>{id.firstLetterToUppercase()}</InputGroup.Text>
      <Form.Control
        id="min"
        type={type}
        step={step}
        placeholder="min"
        onChange={change}
        className={get(filtersGlobal, id + '.min', '').length ? 'bg-warning' : ''}
        value={get(filters, id + '.min', '')}
      />
      <Form.Control
        id="max"
        type={type}
        step={step}
        placeholder="max"
        onChange={change}
        className={get(filtersGlobal, id + '.max', '').length ? 'bg-warning' : ''}
        value={get(filters, id + '.max', '')}
      />
    </InputGroup>
  );
};
