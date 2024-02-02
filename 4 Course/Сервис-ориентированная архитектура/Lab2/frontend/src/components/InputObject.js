import { Form, InputGroup } from 'react-bootstrap';
import { InputField } from './InputField';

export const InputObject = ({ id, fields }) => {
  return (
    <>
      <Form.Label htmlFor={id}>{id.firstLetterToUppercase()}</Form.Label>
      <InputGroup className="mb-3" id={id}>
        {fields.map((field) => (
          <InputField
            key={id + '.' + field}
            id={id + '.' + field}
            isEmbedded={true}
            type={field === 'name' ? 'text' : 'number'}
          />
        ))}
      </InputGroup>
    </>
  );
};
