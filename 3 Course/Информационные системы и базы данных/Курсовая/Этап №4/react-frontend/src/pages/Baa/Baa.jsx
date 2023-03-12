import * as React from "react";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import DisplayCard from "../../components/card";
import Grid from "@mui/material/Unstable_Grid2";
import { Paper, TextField } from "@mui/material";
import { Stack } from "@mui/system";
import { styled } from "@mui/material/styles";
import { useNavigate } from "react-router-dom";
import { baaList } from "../../helpers/baa";
import { useEffect } from "react";
import axios from "axios";

const WhiteBorderTextField = styled(TextField)`
  & label.Mui-focused {
    color: white;
  }
  & .MuiOutlinedInput-root {
    &.Mui-focused fieldset {
      border-color: white;
    }
  }
`;

export default function Baa(props) {
  const [searchText, setSearchText] = React.useState("");
  const [diplayList, setDisplayList] = React.useState([]);
  const [number, setNumber] = React.useState(0);

  const navigate = useNavigate();

  let h = window.innerHeight;
  let gridHeight = h - 60;

  useEffect(() => {
    const url = "http://localhost:32456/get/baa?count=" + number;
    axios({
      method: "get",
      url: url,
    }).then(function (response) {
      setDisplayList(response.data.body);
    });
  }, []);

  const filteredList = diplayList.filter((item) => {
    const name = item.name.toLocaleLowerCase();
    const manufacturer = item.manufacturer.toLocaleLowerCase();
    return name.includes(searchText) || manufacturer.includes(searchText);
  });

  const sectionStyle = {
    backgroundImage:
      "url('https://media.tenor.com/QUNK-PQyiCQAAAAd/chips-verypog.gif')",
    backgroundRepeat: "no-repeat",
    backgroundSize: "cover",
    backgroundPosition: "right -340px bottom",
  };

  const handleSearchTextFieldChange = (event) => {
    setSearchText(event.target.value.toLocaleLowerCase());
  };

  const handleNumberTextFieldChange = (event) => {
    setNumber(event.target.value);
  };

  const handleSortByRating = (event) => {
    const url = "http://localhost:32456/get/baa?count=" + number;
    axios({
      method: "get",
      url: url,
    }).then(function (response) {
      setDisplayList(response.data.body);
    });
  };

  const handleSortByUses = (event) => {
    const url =
      "http://localhost:32456/get/baa?count=" + number + "&order_type=uses";
    axios({
      method: "get",
      url: url,
    }).then(function (response) {
      setDisplayList(response.data.body);
    });
  };

  const handleCardClicked = (event) => {
    const clickedId = event.currentTarget.getAttribute("name");
    console.log(clickedId);
    const url = "/sportsman/baa/" + clickedId;
    navigate(url);
  };

  return (
    <Box sx={{ height: gridHeight }}>
      <Grid
        container
        direction="row"
        alignItems="center"
        justifyContent="center"
        sx={{ height: "100%", p: "10px" }}
      >
        <Grid xs={4} sx={{ height: "100%", p: "10px" }}>
          <Paper elevation={3} sx={{ height: "100%" }}>
            <Box
              sx={{
                height: "100%",
              }}
              style={sectionStyle}
            >
              <Stack
                textAlign="center"
                spacing={3}
                sx={{
                  height: "100%",
                  display: "flex",
                  justifyContent: "center",
                  alignItems: "center",
                  backgroundColor: "rgba(8,8,8,0.3)",
                }}
              >
                <Box sx={{ color: "#ffffff" }}>Укажите сколько получить:</Box>
                <WhiteBorderTextField
                  type="number"
                  onChange={handleNumberTextFieldChange}
                  sx={{
                    input: {
                      color: "#ffffff",
                      border: "2px solid #ffffff",
                    },
                  }}
                />{" "}
                <Button variant="contained" onClick={handleSortByRating}>
                  Получить по рейтингу
                </Button>
                <Button variant="contained" onClick={handleSortByUses}>
                  Получить по использованиям
                </Button>
                <Box sx={{ color: "#ffffff" }}>Искать по ключевому слову:</Box>
                <WhiteBorderTextField
                  onChange={handleSearchTextFieldChange}
                  sx={{
                    input: {
                      color: "#ffffff",
                      border: "2px solid #ffffff",
                    },
                  }}
                />
              </Stack>
            </Box>
          </Paper>
        </Grid>
        <Grid xs={8} sx={{ height: "100%", p: "10px" }}>
          <Paper elevation={3} sx={{ height: "100%" }}>
            <Box
              sx={{
                height: "100%",
                display: "flex",
                flexWrap: "wrap",
                justifyContent: "space-between",
                backgroundColor: "rgba(0,0,0,0)",
                overflow: "auto",
              }}
            >
              {filteredList.map((element) => (
                <DisplayCard
                  key={element.id}
                  name={element.id}
                  title={element.name}
                  onCardClick={handleCardClicked}
                  shortDescription={element.manufacturer}
                ></DisplayCard>
              ))}
            </Box>
          </Paper>
        </Grid>
      </Grid>
    </Box>
  );
}
