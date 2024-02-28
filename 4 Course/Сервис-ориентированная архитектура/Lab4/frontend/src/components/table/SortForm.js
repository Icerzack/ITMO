import Dropdown from 'react-bootstrap/Dropdown';
import { DropdownButton, Form, InputGroup } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';
import { useState } from 'react';
import { useRecoilState, useSetRecoilState } from 'recoil';
import { isDataNeedsToBeUpdatedState, sortState } from '../../state/atoms';
import { sortFieldList } from '../../utils/constants';

export const SortForm = () => {
  const [, setSortGlobal] = useRecoilState(sortState);
  const setIsDataNeedsToBeUpdated = useSetRecoilState(isDataNeedsToBeUpdatedState);
  const [sort, setSort] = useState([]);

  const [fields, setFields] = useState(new Map(sortFieldList.map((x) => [x, false])));

  const sortBy = () => {
    setSortGlobal(sort);
    setIsDataNeedsToBeUpdated(true);
  };

  const add = (event) => {
    setSortGlobal([]);
    const newFields = Object.assign(fields);
    newFields.set(event.target.innerText.slice(1), true);
    setFields(newFields);
    setSort([...sort, event.target.innerText.replaceAll('_', '.')]);
    console.log(sort);
  };

  const pop = () => {
    setSortGlobal([]);
    const newFields = Object.assign(fields);
    newFields.set(sort[sort.length - 1].slice(1), false);
    setFields(newFields);
    setSort(sort.slice(0, -1));
  };

  return (
    <Form id="sortForm" className="mb-4">
      <div className="horizontal-placer">
        <InputGroup.Text>{sort.join()}</InputGroup.Text>
        <DropdownButton variant="dark" title="Добавить">
          {[...fields].map((value) => (
            <div key={value[0]}>
              {!value[1] && (
                <>
                  <Dropdown.Item onClick={add}>{'' + value[0]}</Dropdown.Item>
                  <Dropdown.Item onClick={add}>{'<' + value[0]}</Dropdown.Item>
                </>
              )}
            </div>
          ))}
        </DropdownButton>
        <Button variant="dark" onClick={pop}>
          Убрать
        </Button>
        <Button className="button-3" onClick={sortBy} disabled={sort.length === 0}>
          Сортировать
        </Button>
      </div>
    </Form>
  );
};
