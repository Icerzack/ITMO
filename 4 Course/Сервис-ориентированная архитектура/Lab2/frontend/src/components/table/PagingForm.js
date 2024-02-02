import { Form } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';
import { useEffect, useState } from 'react';
import { useRecoilState, useSetRecoilState } from 'recoil';
import { isDataNeedsToBeUpdatedState, oldPagingState, pagingState } from '../../state/atoms';
import toast from 'react-hot-toast';

export const PagingForm = () => {
  const [paging, setPaging] = useRecoilState(pagingState);
  const [, setOldPaging] = useRecoilState(oldPagingState);
  const setIsDataNeedsToBeUpdated = useSetRecoilState(isDataNeedsToBeUpdatedState);
  const [elementsCount, setElementsCount] = useState('');
  const [pageNumber, setPageNumber] = useState('');
  const [disable, setDisable] = useState(true);

  const click = () => {
    if (pageNumber < '1' && pageNumber !== '') {
      toast.error('Номер страницы всегда > 0!');
      setPageNumber('1');
      return;
    }
    if (elementsCount < '1' && elementsCount !== '') {
      toast.error('Количество всегда > 0!');
      setElementsCount('1');
      return;
    }
    const newPaging = {};
    if (elementsCount.length) newPaging.elementsCount = Number(elementsCount);
    if (pageNumber.length) newPaging.pageNumber = Number(pageNumber);
    setOldPaging(paging);
    setPaging(newPaging);
    setIsDataNeedsToBeUpdated(true);
  };

  useEffect(() => {
    elementsCount === '' && pageNumber === '' ? setDisable(true) : setDisable(false);
  }, [elementsCount, pageNumber]);

  return (
    <Form id="pagingForm">
      <div className="horizontal-placer">
        <Form.Control
          type="number"
          min="1"
          placeholder="Лимит"
          className="form"
          onChange={(event) => {
            setElementsCount(event.target.value);
          }}
        />
        <Form.Control
          type="number"
          min="1"
          placeholder="Номер страницы"
          className="form"
          onChange={(event) => {
            setPageNumber(event.target.value);
          }}
        />
        <Button className="button-3 form" onClick={click} disabled={disable}>
          Применить
        </Button>
      </div>
    </Form>
  );
};
