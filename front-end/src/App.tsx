import React from "react";
import styled from "styled-components";

const Container = styled.div`
  h1 {
    font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
      "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
  }
`;

const App: React.VFC = () => {
  return (
    <Container>
      <div>
        <h1>Hello, World!</h1>
      </div>
    </Container>
  );
};

export default App;
