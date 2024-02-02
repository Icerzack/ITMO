import { Form, InputGroup } from 'react-bootstrap';
import React from 'react';
import set from 'lodash.set';
import get from 'lodash.get';
import { useRecoilState } from 'recoil';
import { filtersState } from '../../state/atoms';

export const FilterRow = ({ filters, setFilters, id, type = 'number', step = 'any' }) => {
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
        id="eq"
        type={type}
        step={step}
        placeholder="="
        onChange={change}
        value={get(filters, id + '.eq', '')}
      />
      <Form.Control
        id="neq"
        type={type}
        step={step}
        placeholder="!="
        onChange={change}
        value={get(filters, id + '.neq', '')}
      />
      <Form.Control
        id="gt"
        type={type}
        step={step}
        placeholder=">"
        onChange={change}
        value={get(filters, id + '.gt', '')}
      />
      <Form.Control
        id="lt"
        type={type}
        step={step}
        placeholder="<"
        onChange={change}
        value={get(filters, id + '.lt', '')}
      />
      <Form.Control
        id="get"
        type={type}
        step={step}
        placeholder=">="
        onChange={change}
        value={get(filters, id + '.get', '')}
      />
      <Form.Control
        id="let"
        type={type}
        step={step}
        placeholder="<="
        onChange={change}
        value={get(filters, id + '.let', '')}
      />
    </InputGroup>
  );
};
