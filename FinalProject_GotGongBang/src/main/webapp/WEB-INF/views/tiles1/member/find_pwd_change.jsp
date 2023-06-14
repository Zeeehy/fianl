<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ctxPath = request.getContextPath();

%>


  <style>

    .container {
      margin: 20px;
      width: 200px;
      height: 200px;
      perspective: 1000px;
    }

    .card {
        position: relative;
      transition: transform 1s;
      transform-style: preserve-3d;
      cursor: pointer;
    }

    .front, .back {
      position: absolute;
      display: flex;
      justify-content: center;
      align-items: center;
      backface-visibility: hidden;
    }

    .front {
      border: 2px solid black;
    }

    .back {
      border: 2px solid black;
      transform: rotateY(180deg);
    }
    /*  */
    
    .login-related__description {
	    font-size: 20px;
	    letter-spacing: -.4px;
	    padding-top: 20px;
	}    
	.login-related__buttons {
	    text-align: center;
	}
    .login-related__button {
	    width: 290px;
	}
    
  </style>
  
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script>
	$(document).ready(function(){
		let card = document.querySelector('.card');
		card.addEventListener('click', click);
		
		
		$(".form-field__feedback").hide();
		
		// 비밀번호 blur
		$("input#pwd").blur( (e) => {
			
			const regExp = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g;
	    	  
	    	const bool = regExp.test($(e.target).val());
			
			// 공백일 때
			if( $(e.target).val().trim() == "") {
				$("input#pwd").addClass("form-input--invalid"); // 유효성 검사 불합격 시 input 붉은색 표시
				$(".user_passwd_f2").hide();
				$(".user_passwd_f1").show(); // 공백 경고 표시
				
				
				flag_input_passwd = false;
			}
			// 형식에 맞지 않을 때
			else if(!bool) {
				$("input#pwd").addClass("form-input--invalid"); // 유효성 검사 불합격 시 input 붉은색 표시
				$(".user_passwd_f1").hide();
				$(".user_passwd_f2").show(); // 공백 경고 표시
				
				flag_input_passwd = false;
			}
			
			else {
				$("input#pwd").removeClass("form-input--invalid");
				$(".user_passwd_f1").hide();
				$(".user_passwd_f2").hide();
				
				flag_input_passwd = true;
			}
		});
		
		// 비밀번호확인 blur
		$("input#user_passwd_check").blur( (e) => {
			
			if( $("input#pwd").val() != $(e.target).val() ) {
				$("input#user_passwd_check").addClass("form-input--invalid"); // 유효성 검사 불합격 시 input 붉은색 표시
				$(".user_passwd_check_f").show(); // 경고 표시
				
				flag_input_passwd_check = false;
			}
			else {
				$("input#user_passwd_check").removeClass("form-input--invalid");
				$(".user_passwd_check_f").hide();
				
				flag_input_passwd_check = true;
			}
		});
	});
	


	function click(event) {
	let elem = event.currentTarget;
	if (elem.style.transform == "rotateY(180deg)") {
	          elem.style.transform = "rotateY(0deg)";
	      } else {
	          elem.style.transform = "rotateY(180deg)";
	      }
	  }
	

    
</script>

<body>
<div id="content">
		<section class="login-search">
			<header class="login-search__header">
				<h2 class="login-search__title">비밀번호 재설정</h2>
				<p class="login-related__description">비밀번호는 8자~15자의 영문, 숫자, 특수문자(@$!%?&)를 <br>조합하여 설정해주세요.</p>
			</header>
			<form class="login-related__form" method="post" action="/user/change-pw">
			<input value="${id}">
				
				<fieldset class="login-search__filedset">
					<div class="form-field">						
						<input class="form-input" type="password" id="pwd" name="pwd" minlength="8" maxlength="15" title="비밀번호 입력" placeholder="비밀번호 입력">
						<div class="form-field__feedback user_passwd_f1" data-field-feedback="user_passwd"><i class="fa-solid fa-circle-exclamation" style="color: #f20707;"></i>&nbsp;&nbsp;비밀번호를 입력하세요.</div>
						<div class="form-field__feedback user_passwd_f2" data-field-feedback="user_passwd"><i class="fa-solid fa-circle-exclamation" style="color: #f20707;"></i>&nbsp;&nbsp;비밀번호 형식이 올바르지 않습니다.</div>
					</div>
					
					<div class="form-field">
						<input class="form-input" type="password" id="user_passwd_check" name="re_user_pw" minlength="8" maxlength="20" title="비밀번호 재입력" placeholder="비밀번호 확인">
						<div class="form-field__feedback user_passwd_check_f" data-field-feedback="user_passwd_check"><i class="fa-solid fa-circle-exclamation" style="color: #f20707;"></i>&nbsp;&nbsp;비밀번호를 재입력하세요.</div>
					</div>
				</fieldset>
				<div class="login-related__buttons">
					<button class="login-related__button login-related__button--confirm button" type="submit">완료</button>
				</div>
			</form>
		</section>

</div>
	
<div class="site-top">
    <a href="#">
        <span>TOP</span>
    </a>
</div>

<div class = 'container'>
    <div class = 'card'>
        <div class = 'front'>
        front
        </div>
        <div class= 'back'>
        back
        </div>
    </div>
</div>




</body>

