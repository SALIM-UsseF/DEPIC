require 'test_helper'

class AdministrateurControllerTest < ActionDispatch::IntegrationTest
  test "should get index" do
    get administrateur_index_url
    assert_response :success
  end

end
